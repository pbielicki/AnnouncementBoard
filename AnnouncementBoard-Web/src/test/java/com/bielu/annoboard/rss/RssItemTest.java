package com.bielu.annoboard.rss;

import static org.junit.Assert.*;

import org.junit.Test;

public class RssItemTest {

	@Test
	public void testToString() {
		RssItem item = new RssItem();
		assertEquals("(RssItem: [null])", item.toString());
		
		item.setTitle("title");
		assertEquals("(RssItem: [title])", item.toString());
	}

}
