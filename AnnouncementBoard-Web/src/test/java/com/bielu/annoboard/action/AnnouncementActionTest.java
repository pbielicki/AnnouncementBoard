package com.bielu.annoboard.action;

import static com.opensymphony.xwork2.ActionContext.getContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.action.rss.RssActionFreeTextSearchTest;
import com.bielu.annoboard.action.rss.RssActionTest;
import com.bielu.annoboard.common.BaseActionTest;
import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.dao.LocationDao;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.domain.Location;
import com.bielu.annoboard.domain.User;

public class AnnouncementActionTest extends BaseActionTest {

	private static final int TWENTY = 20;

	@Autowired
	private AnnouncementAction action;
	
	@Autowired
	private AnnouncementSearchAction searchAction;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private AnnouncementDao announcementDao;
	
	private Announcement[] announcements;
	private Location[] locations;

	@Before
	@Transactional
	public void setUp() {
		User user = RssActionTest.buildUser(1);
		userDao.saveOrUpdate(user);
		
		announcements = new Announcement[TWENTY];
		locations = new Location[TWENTY];
		for (int i = 0; i < TWENTY; i++) {
			Announcement a = RssActionTest.buildAnnouncement(i);
			locations[i] = a.getLocation();
			a.setLocation(null);
			announcements[i] = a;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(User.class.getName(), user);
		getContext().setSession(map);
	}
	
	@After
	@Transactional
	public void tearDown() {
		RssActionFreeTextSearchTest.tearDown(locationDao, announcementDao, userDao);
	}
	
	@Test
	public void testInitCreate() {
		assertNull(action.getAnnouncement());
		assertEquals("input", action.initCreate());
		Announcement a = action.getAnnouncement();
		assertNotNull(a);
		assertNotNull(a.getLocation());
		assertEquals(Locale.getDefault().getCountry().toUpperCase(), a.getLocation().getCountryCode());
		assertSame(a.getLocation().getAnnouncement(), a);
	
		// second invocation
		getContext().setLocale(null);
		assertEquals("input", action.initCreate());
		Announcement a0 = action.getAnnouncement();
		assertEquals(Locale.getDefault().getCountry().toUpperCase(), a0.getLocation().getCountryCode());
		
		// third invocation
		getContext().setLocale(new Locale("en", "AU"));
		assertEquals("input", action.initCreate());
		Announcement a1 = action.getAnnouncement();
		assertEquals("AU", a1.getLocation().getCountryCode());

		// fourth invocation
		getContext().setLocale(new Locale("pl"));
		assertEquals("input", action.initCreate());
		Announcement a2 = action.getAnnouncement();
		assertEquals("PL", a2.getLocation().getCountryCode());
	}
	
	@Test
	public void testValidateUnauthenticated() {
		getContext().getSession().remove(User.class.getName());
		try {
			action.validate();
		} catch (RuntimeException e) {
			fail("[" + e.toString() + "] not expected");
		}
	}
	
	@Test
	public void testCreate() {
        action.setAnnouncement(null);
        assertEquals("input", action.create());
        assertNotNull(action.getAnnouncement());
        assertNotNull(action.getAnnouncement().getLocation());
        assertEquals(2, action.getAnnouncement().getLocation().getCountryCode().length());
		
		for (int i = 0; i < TWENTY; i++) {
			announcements[i].setLocation(locations[i]);
			action.setAnnouncement(announcements[i]);
			assertEquals("success", action.create());
			
			searchAction.setId(announcements[i].getId());
			assertEquals("announcement", searchAction.search());
			assertDeep(announcements[i], searchAction.getAnnouncement());
		}
	}

	@Test
	public void testCreateAndSearch() {
		for (int i = 0; i < TWENTY; i++) {
			announcements[i].setLocation(locations[i]);
			action.setAnnouncement(announcements[i]);
			assertEquals("success", action.create());
		}

		List<Announcement> list = assertListInfo();
		
		for (int i = 0; i < TWENTY; i++) {
			// XXX: assuming correct sequence - could fail for some databases
			assertDeep(announcements[TWENTY - 1 - i], list.get(i));
		}

		searchAction.setId(-1);
		assertListInfo();

		searchAction.setId(0);
		assertListInfo();
	}

	@Test
	public void testCategories() {
		getContext().setLocale(new Locale("test"));
		assertEquals(Arrays.asList(new String[] {"Apartment", "House", "Flat", "Other"}), action.getCategories());
	}
	
	private List<Announcement> assertListInfo() {
		assertEquals("search", searchAction.search());
		List<Announcement> list = searchAction.getAnnouncementList();
		assertNotNull("Announcements list is empty", list);
		assertEquals(TWENTY, list.size());
		
		return list;
	}
	
	private void assertDeep(Announcement a1, Announcement a2) {
		assertNotSame(a1, a2);

		assertTrue("Anouncements [" + a1 + "] and [" + a2 + "] are not equal.", 
				EqualsBuilder.reflectionEquals(a1, a2,
						new String[] { "creationDate", "modificationDate", "location" }));
		
		assertEquals(a1.getCreationDate().getTimeInMillis() / 1000, a2.getCreationDate().getTimeInMillis() / 1000);
		assertEquals(a1.getModificationDate().getTimeInMillis() / 1000, 
					 a2.getModificationDate().getTimeInMillis() / 1000);
		
		assertDeep(a1.getLocation(), a2.getLocation());
	}

	private void assertDeep(Location o1, Location o2) {
		assertTrue("Locations [" + o1 + "] and [" + o2 + "] are not equal.", 
				EqualsBuilder.reflectionEquals(o1, o2,
						new String[] { "announcement", "latitude", "longitude" }));
		
		assertEquals(o1.getLatitude(), o2.getLatitude(), 0.0005d);
		assertEquals(o1.getLongitude(), o2.getLongitude(), 0.0005d);
	}
}
