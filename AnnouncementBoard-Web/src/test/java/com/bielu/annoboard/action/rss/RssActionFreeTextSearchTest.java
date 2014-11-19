package com.bielu.annoboard.action.rss;

import static com.bielu.annoboard.action.rss.RssActionTest.CATEGORY_DATA;
import static com.bielu.annoboard.action.rss.RssActionTest.TITLE_DATA;
import static com.bielu.annoboard.action.rss.RssActionTest.buildAnnouncement;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.common.BaseActionTest;
import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.dao.DaoIntegrityException;
import com.bielu.annoboard.dao.LocationDao;
import com.bielu.annoboard.dao.UserDao;
import com.bielu.annoboard.dao.impl.DaoImpl;
import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.rss.RssChannel;
import com.bielu.annoboard.rss.RssItem;

public class RssActionFreeTextSearchTest extends BaseActionTest {

	private static final int TEN = 10;

	@Autowired
	private RssAction action;
	
	@Autowired
	private AnnouncementDao announcementDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Before
	@Transactional
	public void setUp() throws DaoIntegrityException {
		Announcement[] aa = new Announcement[TEN];
		for (int i = 0; i < TEN; i++) {
			aa[i] = buildAnnouncement(i);
		}
		
		for (int i = 0; i < TEN; i++) {
			userDao.saveOrUpdate(aa[i].getCreator());
			announcementDao.saveOrUpdate(aa[i]);
		}
	}
	
	@After
	@Transactional
	public void tearDown() {
		tearDown(locationDao, announcementDao, userDao);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public static void tearDown(LocationDao locationDao, AnnouncementDao announcementDao, UserDao userDao) {
		locationDao.deleteAll();
		announcementDao.deleteAll();
		userDao.deleteAll();
		((DaoImpl<Announcement>) announcementDao).purgeLuceneIndex();
	}
	
	@Test
	public void testFreeTextSearch() {
		for (int i = 0; i < 10; i++) {
			int n = (int) (TEN * Math.random());
			action.setQuery("description" + n);
			doFreeTextAssert(n);
		}
	}

	@Test
	public void testFreeTextSearchByAddress() {
		for (int i = 0; i < 10; i++) {
			int n = (int) (TEN * Math.random());
			action.setQuery("city" + n);
			doFreeTextAssert(n);
		}
	}

	@Test
	public void testFreeTextSearchWithMultipleResponses() {
		ServletActionContext.setRequest(null);
		
		action.setQuery("some");
		// it will not have any effect
		action.setLastCount(TEN - 2);
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		assertEquals(TEN, action.getChannel().getItems().size());
		
		for (int i = 0; i < TEN; i++) {
			assertEquals(String.format("%s%d - %d%s%d", CATEGORY_DATA, (9 - i), (9 - i), TITLE_DATA, (9 - i)), 
					action.getChannel().getItems().get(i).getTitle());
		}
	}
	
	@Test
	public void testFreeTextSearchWithEmptyResponse() {
		action.setQuery("non-existing-announcement\u00A5");
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		assertEquals(0, action.getChannel().getItems().size());	
	}

	@Test
	public void testFreeTextSearchWithInvalidQuery() {
		action.setQuery("#$%^&%^&(");
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		assertEquals(0, action.getChannel().getItems().size());
	}

	@Test
	public void testFreeTextSearchWithEmptyQuery() {
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		assertEquals(TEN, action.getChannel().getItems().size());

		action.setQuery("");
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		assertEquals(TEN, action.getChannel().getItems().size());

	}

	private void doFreeTextAssert(int n) {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		String url = "http://fakehost:1234/ABoard/rss.action";
		expect(request.getRequestURL()).andReturn(new StringBuffer(url)).times(2);
		expect(request.getServletPath()).andReturn("/rss.action").times(2);
		expect(request.getQueryString()).andReturn("param1=value1&param2=value2");
		expect(request.getParameter("param1")).andReturn("value1");
		expect(request.getParameter("param2")).andReturn("value2");
		replay(request);
		ServletActionContext.setRequest(request);
		
		assertEquals("success", action.execute());
		assertNotNull(action.getChannel());
		
		RssChannel channel = action.getChannel();
		assertEquals(String.format("%s%s%s", url, "?query=", action.getQuery()), channel.getLink());
		
		List<RssItem> items = channel.getItems();
		assertEquals(action.getText("channel.title.freeText", new String[] { action.getQuery(),
				String.valueOf(channel.getItems().size()) }), channel.getTitle());

		assertNotNull(items);
		assertEquals(1, items.size());
		RssItem item = items.get(0);
		assertEquals("some description" + n, item.getDescription());
		assertEquals("appartment" + n + " - " + n + "some title " + n, item.getTitle());
		assertEquals(10, item.getImages().size());
		for (int i = 0; i < 10; i++) {
			assertEquals("image/" + n + "_" + i, item.getImages().get(i));
		}
		
		// do not verify Mocks
	}
}
