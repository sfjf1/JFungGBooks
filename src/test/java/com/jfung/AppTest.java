package com.jfung;

import org.junit.Test;
import org.junit.Assert;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class AppTest {

	@Test
	public void testGetBooks() {
		String results = HttpHelper.getBooks("https://www.googleapis.com/books/v1/volumes"
				+ "?q=The+Digital+Photography+Book+Scott+Kelby&maxResults=10");
		Assert.assertTrue(results.contains("The Digital Photography Book"));
	}
	
	@Test
	public void testCreateBookshelf() {
		ObjectNode bookshelf = JsonHelper.createBookshelf();
		Assert.assertEquals("{\"items\":[]}", bookshelf.toString());
	}
	
	@Test
	public void testGetItem() {
		String items = "[{\"id\":\"123\"}, {\"id\":\"456\"}]";
		Assert.assertEquals("{\"id\":\"123\"}", JsonHelper.getItem(items, "123"));
	}
	
	
	// ========================================
	// Negative test cases for error handling
	// ========================================
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetItemsNegative() {
		JsonHelper.getItem(null, "");
		JsonHelper.getItem(null, null);
		JsonHelper.getItem("", "");
		JsonHelper.getItem("", null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddBookNegative() {
		JsonHelper.addBook(null, "");
		JsonHelper.addBook(null, null);
	}
}
