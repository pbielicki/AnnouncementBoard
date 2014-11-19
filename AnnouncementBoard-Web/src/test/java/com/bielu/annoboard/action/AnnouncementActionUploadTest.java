package com.bielu.annoboard.action;

import static com.opensymphony.xwork2.ActionContext.getContext;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.action.rss.RssActionTest;
import com.bielu.annoboard.common.BaseActionTest;
import com.bielu.annoboard.domain.User;


public class AnnouncementActionUploadTest extends BaseActionTest {

	@Autowired
	private AnnouncementAction action;
	
	private ArrayList<File> fileList;
	private ArrayList<String> nameList;
	private User creator;
	private ServletContext context;
	private String dir;
	
	@Before
	@Transactional
	public void setUp() throws IOException {
		context = createMock(ServletContext.class);
		dir = "images";
		ServletActionContext.setServletContext(context);
		creator = RssActionTest.buildUser(1);
		action.setAnnouncement(RssActionTest.buildAnnouncement(1));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(User.class.getName(), creator);
		getContext().setSession(map);

		FileUtils.deleteDirectory(new File(dir));

		initUploadFiles();
	}

	private void initUploadFiles() throws IOException {
		final int magic = 5;
		fileList = new ArrayList<File>(magic);
		nameList = new ArrayList<String>(magic);
		
		for (int i = 0; i < magic; i++) {
			File file = new File("test" + (i + 1) + ".txt");
			FileWriter out = new FileWriter(file);
			out.append("some text for the " + (i + 1) + " file");
			out.close();
			
			fileList.add(file);
			nameList.add("fileName" + (i + 1) + ".xml");
		}
	}
	
	@After
	public void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File(dir));
		for (File file : fileList) {
			file.delete();
		}
		
		verify(context);		
	}
	
	@Test
	public void testMoveUploadedFilesToDestDirAllFiles() throws Exception {
		expect(context.getRealPath("images/")).andReturn(dir);
		replay(context);

		action.setUpload(new ArrayList<File>());
		action.setUploadFileNames(new ArrayList<String>());

		action.setUpload0(fileList.get(0));
		action.setUpload0FileName(nameList.get(0));
		
		action.setUpload1(fileList.get(1));
		action.setUpload1FileName(nameList.get(1));
		
		action.setUpload2(fileList.get(2));
		action.setUpload2FileName(nameList.get(2));
		
		action.setUpload3(fileList.get(3));
		action.setUpload3FileName(nameList.get(3));
		
		action.setUpload4(fileList.get(4));
		action.setUpload4FileName(nameList.get(4));
		
		action.validate();
		for (int i = 0; i < 5; i++) {
			File file = new File(String.format("%s/%d/%s%d.%s", 
					dir, creator.getId(),	"fileName", i + 1, "xml"));
			
			assertTrue(String.format("File [%s] does not exist", file), file.exists());
			assertEquals("some text for the " + (i + 1) + " file", FileUtils.readFileToString(file));
			assertEquals(file.getName(), action.getUploadFileNames().get(i));
			assertEquals(file.getName(), action.getAnnouncement().getImagesList().get(i));
			file.delete();
		}
	}
	
	@Test
	public void testMoveUploadedFilesToDestDirTwoFilesThreeTimes() throws Exception {
		expect(context.getRealPath("images/")).andReturn(dir).times(3);
		replay(context);

		uploadTwoMiddleFiles("");
		
		initUploadFiles();
		uploadTwoMiddleFiles("1_");
		
		initUploadFiles();
		uploadTwoMiddleFiles("2_");
	}

	private void uploadTwoMiddleFiles(String prefix) throws IOException {
		action.setUpload(new ArrayList<File>());
		action.setUploadFileNames(new ArrayList<String>());
		
		action.setUpload2(fileList.get(0));
		action.setUpload2FileName(nameList.get(0));
		
		action.setUpload3(fileList.get(1));
		action.setUpload3FileName(nameList.get(1));
		
		action.validate();
		for (int i = 0; i < 2; i++) {
			File file = new File(String.format("%s/%d/%s%s%d.%s", 
					dir, creator.getId(), prefix, "fileName", i + 1, "xml"));
			
			assertTrue(String.format("File [%s] does not exist", file), file.exists());
			assertEquals("some text for the " + (i + 1) + " file", FileUtils.readFileToString(file));
			assertEquals(file.getName(), action.getUploadFileNames().get(i));
			assertEquals(file.getName(), action.getAnnouncement().getImagesList().get(i));
		}
	}
}
