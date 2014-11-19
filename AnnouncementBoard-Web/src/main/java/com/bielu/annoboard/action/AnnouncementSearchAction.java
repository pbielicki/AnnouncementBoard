package com.bielu.annoboard.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.action.common.GetQueryAction;
import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.domain.Announcement;

public class AnnouncementSearchAction extends GetQueryAction {

	private static final long serialVersionUID = -4588129660511940784L;
	private static final String SEARCH = "search";
	private static final String ANNOUNCEMENT = "announcement";
	
	@Autowired
	private AnnouncementDao announcementDao = null;

	private Announcement announcement = null;
	private List<Announcement> list = null;
	private int lastCount = Integer.MAX_VALUE;
	private long id = 0;

	@Transactional(readOnly = true)
	public String search() {
		if (isQueryEmpty() == false) {
			list = announcementDao.loadByFreeTextQuery(query);
			return SEARCH;
		}
		
		if (id <= 0) {
			list = announcementDao.loadLast(lastCount);
			return SEARCH;
		} else {
			announcement = announcementDao.findById(id);
			return ANNOUNCEMENT;
		}
	}

	public List<Announcement> getAnnouncementList() {
		return list;
	}
	
	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setLastCount(int lastCount) {
		this.lastCount = lastCount;
	}
}