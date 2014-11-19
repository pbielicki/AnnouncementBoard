package com.bielu.annoboard.action.rss;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.domain.Location;
import com.bielu.annoboard.domain.User;
import com.bielu.annoboard.rss.RssChannel;
import com.bielu.annoboard.rss.RssItem;

public class RssActionTest extends BaseActionTest {

	protected static final String TITLE_DATA = "some title ";
	protected static final String DESCRIPTION_DATA = "some description";
	protected static final String CATEGORY_DATA = "appartment";
	private static final int FIFTY_ONE = 51;
	private static final int THIRTY = 30;
	private static final String FORMAT = "yyyy-MM-dd HH";

	@Autowired
	private RssAction rssAction;
	
	@Autowired
	private AnnouncementDao announcementDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LocationDao locationDao;
	
	private HttpServletRequest request;
	
	@Before
	@Transactional(readOnly = false)
	public void setUp() throws DaoIntegrityException {
		request = createMock(HttpServletRequest.class);
		
		String url = "http://fakehost:1234/ABoard/rss.action";
		expect(request.getRequestURL()).andReturn(new StringBuffer(url)).times(FIFTY_ONE * 2);
		expect(request.getServletPath()).andReturn("/rss.action").times(FIFTY_ONE);

		replay(request);
		ServletActionContext.setRequest(request);
		
		Announcement[] aa = new Announcement[FIFTY_ONE];
		for (int i = 0; i < FIFTY_ONE; i++) {
			aa[i] = buildAnnouncement(i);
		}
		
		for (int i = 0; i < FIFTY_ONE; i++) {
			userDao.saveOrUpdate(aa[i].getCreator());
			announcementDao.saveOrUpdate(aa[i]);
		}
	}
	
	@After
	@Transactional(readOnly = false)
	public void tearDown() {
		RssActionFreeTextSearchTest.tearDown(locationDao, announcementDao, userDao);
	}
	
	@Test
	public void testExecuteDefaults() {
		assertNotNull(rssAction);
		assertNotNull(announcementDao);
		
		assertEquals("success", rssAction.execute());
		assertNotNull(rssAction.getChannel());
		
		List<RssItem> items = rssAction.getChannel().getItems();
		assertNotNull(items);
		assertEquals(50, items.size());
	}

	@Test
	public void testExecuteLastCount() {
		assertNotNull(rssAction);
		assertNotNull(announcementDao);

		rssAction.setLastCount(200);
		assertEquals("success", rssAction.execute());
		assertNotNull(rssAction.getChannel());
		
		List<RssItem> items = rssAction.getChannel().getItems();
		assertNotNull(items);
		assertEquals(FIFTY_ONE, items.size());
	}
		
	@Test
	public void testExecuteDeepCheck() throws ParseException {
		request = createMock(HttpServletRequest.class);
		String url = "http://fakehost:1234/ABoard/rss.action";
		expect(request.getRequestURL()).andReturn(new StringBuffer(url)).times(THIRTY + 1);
		expect(request.getServletPath()).andReturn("/rss.action").times(THIRTY);
		replay(request);
		ServletActionContext.setRequest(request);
		
		rssAction.setLastCount(THIRTY);
		assertEquals("success", rssAction.execute());
		assertNotNull(rssAction.getChannel());
		
		RssChannel channel = rssAction.getChannel();
		List<RssItem> items = channel.getItems();
		assertEquals(rssAction.getText("channel.title", new String[] {"" + THIRTY}), channel.getTitle());

		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		assertEquals(format.format(new Date()), format.format(format.parse(channel.getPubDate())));
		assertEquals(format.format(new Date()), format.format(format.parse(channel.getLastBuildDate())));
		
		assertNotNull(items);
		assertEquals(THIRTY, items.size());
		
		assertEquals("http://fakehost:1234/ABoard/rss.action", channel.getLink());
		
		for (int i = 0; i < THIRTY; i++) {
			RssItem item = items.get(i);
			
			int idx = item.getGuid().indexOf("id=");
			assertTrue("[id=] substring is not present in guid [" + item.getGuid() + "]", idx > -1);
			
			assertEquals("http://fakehost:1234/ABoard/search.jsps?", item.getGuid().substring(0, idx));
			assertEquals(item.getGuid(), item.getLink());
			
			String titleRegex = "appartment\\d++ - \\d++some title \\d++";
			assertTrue("[" + item.getTitle() + "] does not match [" + titleRegex + "]", 
						item.getTitle().matches(titleRegex));

			String descRegex = "some description\\d++";
			assertTrue("[" + item.getDescription() + "] does not match [" + descRegex + "]", 
						item.getDescription().matches(descRegex));

			assertEquals(format.format(new Date().getTime()), format.format(format.parse(item.getPubDate())));

			String userRegex = "user\\d++";
			assertTrue("[" + item.getCreator() + "] does not match [" + userRegex   + "]", 
					item.getCreator().matches(userRegex));

			String imageRegex = "image/\\d++_\\d";
			assertEquals(10, item.getImages().size());
			assertTrue("[" + item.getImages().get(0) + "] does not match [" + imageRegex  + "]", 
						item.getImages().get(0).matches(imageRegex));
		}
		
		verify(request);
	}
	
	public static Announcement buildAnnouncement(int id) {
		Announcement a = new Announcement();
		a.setCategory(CATEGORY_DATA + id);
		a.setDescription(DESCRIPTION_DATA + id);
		a.setTitle(id + TITLE_DATA + id);
		for (int i = 0; i < 10; i++) {
			a.addImageLocation("image/" + id + "_" + i);
		}
		a.setCreator(buildUser(id));
		a.setLocation(buildLocation(id));
		a.getLocation().setAnnouncement(a);
		
		return a;
	}

	private static Location buildLocation(int id) {
		Location l = new Location();
		l.setLatitude(-90 + (180 * Math.random()));
		l.setLongitude(-180 + (360 * Math.random()));
		l.setAddress("address" + id);
		l.setCity("city" + id);
		
		return l;
	}

	public static User buildUser(int id) {
		User creator = new User();
		creator.setUsername("user" + id);
		creator.setEmail(creator.getUsername() + "@email.com");
		creator.setFirstName("firstName" + id);
		creator.setLastName("lastName" + id);
		
		return creator;
	}
}
