package com.bielu.annoboard.action.rss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.action.common.GetQueryAction;
import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.rss.RssChannel;

public class RssAction extends GetQueryAction {

	private static final long serialVersionUID = 5247841193591145995L;
	public static final int DEFAULT_LAST_COUNT = 50;

	@Autowired
	private AnnouncementDao announcementDao;
	private RssChannel channel;
	private int lastCount = DEFAULT_LAST_COUNT;

	@Override
	@Transactional(readOnly = true)
	public String execute() {
		if (isQueryEmpty() == false) {
			channel = RssChannelHelper.createRssChannel(announcementDao.loadByFreeTextQuery(query), query);
			channel.setTitle(getText("channel.title.freeText", 
					new String[] { query, String.valueOf(channel.getItems().size()) }));
			return SUCCESS;
		}
		
		List<Announcement> list = announcementDao.loadLast(lastCount);
		channel = RssChannelHelper.createRssChannel(list);
		channel.setTitle(getText("channel.title", new String[] { String.valueOf(list.size()) }));
		return SUCCESS;
	}
	
	public RssChannel getChannel() {
		return channel;
	}
	
	public void setLastCount(int count) {
		this.lastCount = count;
	}
}
