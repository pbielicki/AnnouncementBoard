package com.bielu.annoboard.action;

import static com.opensymphony.xwork2.ActionContext.LOCALE;
import static com.opensymphony.xwork2.ActionContext.getContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.dao.AnnouncementDao;
import com.bielu.annoboard.domain.Announcement;
import com.bielu.annoboard.domain.Location;
import com.bielu.annoboard.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

public class AnnouncementAction extends ActionSupport {

	private static final long serialVersionUID = -4588129660511940784L;
	private static final int UPLOAD_SIZE = 5;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	
	@Autowired
	private AnnouncementDao announcementDao = null;

	private Announcement announcement = null;
	private List<File> upload = new ArrayList<File>(UPLOAD_SIZE);
	private List<String> uploadFileNames = new ArrayList<String>(UPLOAD_SIZE);

	public String initCreate() {
		announcement = new Announcement();
		Location location = new Location();
		location.setCountryCode(extractCountryCode());
		announcement.setLocation(location);
		return INPUT;
	}

	@Transactional
	public String create() {
		if (announcement == null) {
			return initCreate();
		}
		announcement.setCreator((User) getContext().getSession().get(User.class.getName()));
		announcementDao.saveOrUpdate(announcement);
		return SUCCESS;
	}
	
	private String extractCountryCode() {
		if (getContext().get(LOCALE) == null) {
			return Locale.getDefault().getCountry().toUpperCase();
		}
		
		String countryCode = getContext().get(LOCALE).toString();
		if (countryCode.length() > 2) {
			return countryCode.substring(countryCode.length() - 2).toUpperCase();
		}
		return countryCode.toUpperCase();
	}
	
	@Override
	public void validate() {
		if (isAuthenticated() == false) {
			return;
		}
		
		UploadHelper.moveUploadedFilesToDestDir(upload, uploadFileNames);
		if (announcement != null) {
			announcement.setImagesList(uploadFileNames);
		}
		super.validate();
	}

	private boolean isAuthenticated() {
		return getContext().getSession().get(User.class.getName()) != null;
	}

	@VisitorFieldValidator(message = "")
	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public List<String> getCategories() {
		return Arrays.asList(getText("input.categories").split(", "));
	}

	public File getUpload0() {
		return upload.get(0);
	}

	public void setUpload0(File upload0) {
		this.upload.add(upload0);
	}

	public String getUpload0FileName() {
		return uploadFileNames.get(0);
	}

	public void setUpload0FileName(String upload0FileName) {
		this.uploadFileNames.add(upload0FileName);
	}


	public File getUpload1() {
		return upload.get(1);
	}

	public void setUpload1(File upload1) {
		this.upload.add(upload1);
	}

	public String getUpload1FileName() {
		return uploadFileNames.get(1);
	}

	public void setUpload1FileName(String upload1FileName) {
		this.uploadFileNames.add(upload1FileName);
	}

	public File getUpload2() {
		return upload.get(2);
	}

	public void setUpload2(File upload2) {
		this.upload.add(upload2);
	}

	public String getUpload2FileName() {
		return uploadFileNames.get(2);
	}

	public void setUpload2FileName(String upload2FileName) {
		this.uploadFileNames.add(upload2FileName);
	}

	public File getUpload3() {
		return upload.get(THREE);
	}

	public void setUpload3(File upload3) {
		this.upload.add(upload3);
	}

	public String getUpload3FileName() {
		return uploadFileNames.get(THREE);
	}

	public void setUpload3FileName(String upload3FileName) {
		this.uploadFileNames.add(upload3FileName);
	}

	public File getUpload4() {
		return upload.get(FOUR);
	}

	public void setUpload4(File upload4) {
		this.upload.add(upload4);
	}

	public String getUpload4FileName() {
		return uploadFileNames.get(FOUR);
	}

	public void setUpload4FileName(String upload4FileName) {
		this.uploadFileNames.add(upload4FileName);
	}

	protected List<File> getUpload() {
		return upload;
	}

	protected void setUpload(List<File> upload) {
		this.upload = upload;
	}

	protected List<String> getUploadFileNames() {
		return uploadFileNames;
	}

	protected void setUploadFileNames(List<String> uploadFileNames) {
		this.uploadFileNames = uploadFileNames;
	}
}