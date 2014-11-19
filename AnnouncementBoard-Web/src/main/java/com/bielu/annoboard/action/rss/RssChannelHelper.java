package com.bielu.annoboard.action.rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.rss.RssChannel;
import com.bielu.annoboard.rss.RssItem;

final class RssChannelHelper {
	
	private static final String FORMAT = "yyyy-MM-dd HH:mm";
	private static final String ANNOUNCEMENT_DETAILS_ACTION = "search.jsps";

	private static RssItem createItem(Announcement a) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		RssItem item = new RssItem();
		item.setTitle(String.format("%s - %s", a.getCategory(), a.getTitle()));
		item.setDescription(a.getDescription());
		item.setLocation(a.getLocation());
		item.setCreator(a.getCreator().getUsername());
		
		item.setGuid(getItemUrl(a.getId()));
		item.setLink(item.getGuid());
		
		item.setPubDate(format.format(a.getCreationDate().getTime()));
		item.setImages(a.getImagesList());
		return item;
	}

	protected static RssChannel createRssChannel(List<Announcement> list, String query) {
		RssChannel channel = new RssChannel();
		channel.setLink(getChannelUrl(query));
	
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		channel.setPubDate(format.format(new Date()));
		channel.setLastBuildDate(format.format(new Date()));
		
		for (Announcement a : list) {
			channel.addItem(createItem(a));
		}
		
		return channel;
	}
	
	protected static RssChannel createRssChannel(List<Announcement> list) {
		return createRssChannel(list, null);
	}

	private static String getChannelUrl(String query) {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request != null) {
			StringBuffer url = request.getRequestURL();
			if (query != null) {
				url.append("?query=").append(query);
			}

			return url.toString();
		} else {
			return "";
		}
	}

	private static String getItemUrl(long itemId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request != null) {
			String url = request.getRequestURL().toString();
			String servletPath = request.getServletPath();
			
			StringBuilder value = null;
			int idx = url.indexOf(servletPath);
			if (idx > -1) {
				value = new StringBuilder(url.substring(0, idx + 1));
				value.append(ANNOUNCEMENT_DETAILS_ACTION).append("?id=").append(itemId);
			} else {
				throw new IllegalStateException(String.format("Servlet path [%s] " 
						+ "does not exist in the request URL [%s] - this is probably some bug of the servlet conatiner", 
						servletPath, url));
			}
			return value.toString();
		} else {
			return "";
		}
	}

	private RssChannelHelper() {
	}
}
