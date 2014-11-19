package com.bielu.annoboard.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.domain.Announcement;

public class AnnouncementDaoImpl extends DaoImpl<Announcement> implements AnnouncementDao {

	private static final long serialVersionUID = -5682930135484146640L;

	@Override
	protected Class<Announcement> getPersistentClass() {
		return Announcement.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Announcement> loadLast(int lastCount) {
		HibernateTemplate t = getHibernateTemplate();
		t.setMaxResults(lastCount);
		return t.find(String.format("from %s as a order by a.modificationDate desc, a.id desc", getPersistentClassName()));
	}

	@SuppressWarnings("unchecked")
	public List<Announcement> loadByFreeTextQuery(String stringQuery) {
		List<Announcement> list = freeTextSearch(stringQuery, "title", "category", "description", "location.city");
		// sort DESCENDING
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
}
