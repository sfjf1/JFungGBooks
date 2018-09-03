package com.jfung;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class App {

	private static final Scanner scan = new Scanner(System.in);
	private static final String GBOOK_ENDPT = "https://www.googleapis.com/books/v1/volumes";
	private static final String DEFAULT_MAX_RESULTS = "10";
	private static String maxResults;
	private static ObjectNode bookshelf;
	private static String filepath;
	private static String bookItems;
	
	public App() {
		bookshelf = JsonHelper.createBookshelf();
		bookItems = "";
	}
	
	public static void main( String[] args ) {
		App app = new App();
		app.parseCmdArgs(args);

		int choice = 1;		
		while (choice != 6) {
			Helper.displayMenu();
			choice = Integer.parseInt(scan.nextLine());
			
			switch (choice) {
			case 1: 
				// search books
				System.out.println("please enter keywords to search\n");
				String keywords = scan.nextLine();
				String searchResult = app.getBooks(keywords);
				JsonHelper.displayBooksInfo(searchResult);
				bookItems = JsonHelper.getItems(searchResult);
				break;
			case 2: 
				// add book to bookshelf
				System.out.println("please enter book id\n");
				String bookId = scan.nextLine();
				String bookItem = JsonHelper.getItem(bookItems, bookId);
				JsonHelper.addBook(bookshelf, bookItem);
				break;
			case 3: 
				// load books from bookshelf
				bookshelf = JsonHelper.loadBookshelf(filepath);
				break;
			case 4: 
				// display books from bookshelf
				JsonHelper.displayBookshelf(bookshelf);
				break;
			case 5: 
				// save to disk
				JsonHelper.saveToDisk(filepath, bookshelf);
				break;
			case 6: 
				// exit
				System.exit(0);
			default:
				System.out.println("Invalid option : " + choice);
					break;
			}
		}
	}

	public static String getBooks(String keywords) {

		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("q", keywords);
		reqParams.put("maxResults", maxResults);

		List<String> encItems = new LinkedList();
		for (String key : reqParams.keySet()) {
			String encVal = URLEncoder.encode(reqParams.get(key));
			encItems.add(key + "=" + encVal);
		}
		String encKeyVal = String.join("&", encItems);
		String encodedUrl = GBOOK_ENDPT + "?" + encKeyVal;

		String result = HttpHelper.getBooks(encodedUrl);
		return result;
	}

	private void parseCmdArgs(String[] args) {
		Options options = new Options();

		Option optFilePath = new Option("f", "filepath", true, "full path name of bookshelf storage");
		optFilePath.setRequired(true);
		options.addOption(optFilePath);
		
		Option optMaxResults = new Option("m", "maxResults", true, "max no. of results to return");
		optMaxResults.setRequired(false);
		options.addOption(optMaxResults);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
			filepath = cmd.getOptionValue("filepath");
			maxResults = cmd.getOptionValue("maxResults", DEFAULT_MAX_RESULTS);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			formatter.printHelp("App", options);
			System.exit(1);
		}
	}
}
