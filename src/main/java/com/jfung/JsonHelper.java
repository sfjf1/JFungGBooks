package com.jfung;

import java.io.IOException;
import java.io.File;
import java.lang.IllegalArgumentException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {
	private static final int MAX_DESC_LEN = 50;

	// Create a bookshelf object
	public static ObjectNode createBookshelf() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode items = mapper.createObjectNode();
		items.putArray("items");
		return items;
	}

	public static void displayBooksInfo(String bookItems) {
		if (bookItems == null || bookItems.length() == 0) {
			return;
		}

		System.out.println("============================================================");
		System.out.println("[Book ID] : [Title] : [Authors] : [Description (truncated)]");
		System.out.println("============================================================");
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(bookItems);
			JsonNode itemsNode = root.path("items");
			if (itemsNode.isMissingNode()) {
				return;
			} else {
				if (itemsNode.isArray()) {
					for (JsonNode node : itemsNode) {
						String id = node.path("id").asText();
						JsonNode volInfoNode = node.path("volumeInfo");
						String title = volInfoNode.path("title").asText();
						String authors = volInfoNode.path("authors").toString();
						String description = volInfoNode.path("description").asText();
						if (description != null && description.length() >= MAX_DESC_LEN) {
							System.out.println("[" + id + "] : " + title + " : " + authors + " : (" + description.substring(0, 50) + ")");
						} else {
							System.out.println("[" + id + "] : " + title + " : " + authors);
						}
					}
				}
			}

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	// display each book from bookshelf in a simplified format
	public static void displayBookshelf(ObjectNode bookshelf) {
		if (bookshelf == null) {
			return;
		}

		JsonNode itemsNode = bookshelf.path("items");
		if (itemsNode.isMissingNode()) {
			return;
		} else {
			if (itemsNode.isArray()) {
				for (JsonNode node : itemsNode) {
					String id = node.path("id").asText();
					JsonNode volInfoNode = node.path("volumeInfo");
					String title = volInfoNode.path("title").asText();
					String authors = volInfoNode.path("authors").toString();
					String description = volInfoNode.path("description").asText();
					if (description != null && description.length() >= MAX_DESC_LEN) {
						System.out.println("[" + id + "] : " + title + " : " + authors + " : (" + description.substring(0, 50) + ")");
					} else {
						System.out.println("[" + id + "] : " + title + " : " + authors);
					}
				}
			}
		}
	}

	public static void saveToDisk(String filepath, ObjectNode bookshelf) {
		if (filepath == null || filepath.length() == 0) {
			throw new IllegalArgumentException();
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(filepath), bookshelf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ObjectNode loadBookshelf(String filepath) {
		if (filepath == null || filepath.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		JsonNode root = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			root = mapper.readTree(new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (ObjectNode) root;
	}
	
	public static void addBook(ObjectNode bookshelf, String book) {
		if (bookshelf == null || book == null || book.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		try {
			JsonNode items = bookshelf.path("items");
			ObjectMapper mapperBook = new ObjectMapper();
			JsonNode bookNode = mapperBook.readTree(book);
			((ArrayNode) items).add(bookNode);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get all items from the search books result
	public static String getItems(String booksResult) {
		if (booksResult == null || booksResult.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		String bookItemsArrayStr = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(booksResult);
			JsonNode bookItemsArray = root.path("items");
			bookItemsArrayStr = bookItemsArray.toString();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bookItemsArrayStr;
	}

	// get 1 item from the "items" array by bookId
	public static String getItem(String bookItemsArray, String bookId) {
		if (bookItemsArray == null || bookItemsArray.length() == 0 
				|| bookId == null || bookId.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootArray = mapper.readTree(bookItemsArray);
			for (JsonNode node : rootArray) {
				String id = node.path("id").asText();
				if (bookId.equals(id)) {
					return node.toString();
				}
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
