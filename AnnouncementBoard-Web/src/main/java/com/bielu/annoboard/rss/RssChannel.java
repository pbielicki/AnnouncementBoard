package com.bielu.annoboard.rss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RssChannel implements Serializable {

	private static final long serialVersionUID = 6318683006884269139L;

	private String title;
	
	private String link;
	
	private String pubDate;
	
	private String lastBuildDate;
	
	private String generator = "Bielu Services - http://www.bielu.com";
	
	private List<RssItem> items;

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

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getGenerator() {
		return generator;
	}

	public List<RssItem> getItems() {
		if (items == null) {
			return Collections.emptyList();
		}
		return items;
	}

	public void addItem(RssItem item) {
		if (items == null) {
			items = new ArrayList<RssItem>();
		}
		items.add(item);
	}
}