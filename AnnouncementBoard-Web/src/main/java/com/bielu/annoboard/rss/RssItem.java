package com.bielu.annoboard.rss;

import java.util.ArrayList;
import java.util.List;

import com.bielu.annoboard.domain.Location;

public class RssItem {
	
	private String title;
	
	private String link;
	
	private String description;
	
	private String pubDate;
	
	private String guid;
	
	private String creator;

	private List<String> images = new ArrayList<String>();

	private Location location;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public void setCreator(String username) {
		this.creator = username;
	}
	
	public String getCreator() {
		return creator;
	}

	public List<String> getImages() {
		return images;
	}
	
	public void setImages(List<String> images) {
		this.images = images;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return String.format("(RssItem: [%s])", title);
	}
}
