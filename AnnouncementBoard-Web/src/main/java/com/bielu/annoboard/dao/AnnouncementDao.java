package com.bielu.annoboard.dao;

import java.util.List;

import com.bielu.annoboard.domain.Announcement;

public interface AnnouncementDao extends Dao<Announcement> {

	List<Announcement> loadLast(int lastCount);
	
	List<Announcement> loadByFreeTextQuery(String query);
}