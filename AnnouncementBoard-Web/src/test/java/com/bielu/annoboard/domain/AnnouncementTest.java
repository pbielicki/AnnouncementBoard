package com.bielu.annoboard.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import org.junit.Test;

public class AnnouncementTest {

	@Test
	public void testToString() {
		Announcement a1 = new Announcement();
		
		String format = "(Announcement: id[%d] category[%s] title[%s])";
		assertEquals(String.format(format, 0, null, null), a1.toString());
		
		a1.setCategory("category");
		a1.setId(1);
		assertEquals(String.format(format, 1, "category", null), a1.toString());

		a1.setTitle("title");
		a1.setId(11);
		assertEquals(String.format(format, 11, "category", "title"), a1.toString());
	}
	
	@Test
	public void testSetImagesWithNull() {
		Announcement a = new Announcement();
		a.setImages(null);
		assertEquals("", a.getImages());
		assertEquals(Collections.emptyList(), a.getImagesList());
	}

	@Test
	public void testGetImages() {
		Announcement a = new Announcement();
		
		assertEquals("", a.getImages());
		
		a.setImagesList(a.getImagesList());
		assertEquals("", a.getImages());
		assertEquals(Collections.emptyList(), a.getImagesList());

		a.setImages(a.getImages());
		assertEquals("", a.getImages());
		assertEquals(Collections.emptyList(), a.getImagesList());
		
		a.setImagesList(Arrays.asList(new String[] {"image", "image1", "image2" }));
		assertEquals("image; image1; image2", a.getImages());
	}
	
	@Test
	public void testCompareTo() {
		Announcement a1 = new Announcement();
		assertEquals(0, a1.compareTo(a1));
		
		Announcement a2 = new Announcement();
		assertEquals(0, a1.compareTo(a2));
		assertEquals(0, a2.compareTo(a1));
		
		Calendar c = a1.getCreationDate();
		c.add(Calendar.HOUR_OF_DAY, 1);
		a1.setCreationDate(c);
		assertEquals(1, a1.compareTo(a2));
		assertEquals(-1, a2.compareTo(a1));

		a1.setCreationDate(a2.getCreationDate());
		a2.setId(100000L);
		assertEquals(-1, a1.compareTo(a2));
		assertEquals(1, a2.compareTo(a1));
	}
}
