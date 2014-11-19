package com.bielu.annoboard.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@Indexed(index = "announcement")
public class Announcement implements Identifiable, Comparable<Announcement> {

	private static final long serialVersionUID = -803320823465119526L;

	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String title;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String category;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String description;
	
	@IndexedEmbedded(prefix = "location.")
	private Location location;
	
	private Calendar creationDate;
	
	private Calendar modificationDate;
	
	private User creator;

	private List<String> imagesList;
	
	private String images;
	
	@DocumentId
	private long id = 0;
	
	public Announcement() {
		creationDate = Calendar.getInstance();
		modificationDate = Calendar.getInstance();
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@RequiredStringValidator(message = "validation.empty.title", key = "validation.empty.title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@RequiredStringValidator(message = "validation.empty.category", key = "validation.empty.category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@RequiredStringValidator(message = "validation.empty.description", key = "validation.empty.description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@VisitorFieldValidator(message = "")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		if (this.location != null) {
			this.location.setAnnouncement(this);
		}
	}

	public Calendar getCreationDate() {
		return (Calendar) creationDate.clone();
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getModificationDate() {
		return (Calendar) modificationDate.clone();
	}

	public void setModificationDate(Calendar modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public void addImageLocation(String string) {
		if (imagesList == null) {
			imagesList = new ArrayList<String>();
		}
		imagesList.add(string);
		
	}

	public List<String> getImagesList() {
		if (imagesList == null) {
			imagesList = new ArrayList<String>();
		}
		return imagesList;
	}

	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	
	public String getImages() {
		if (imagesList == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for (String image : imagesList) {
			if (first == true) {
				first = false;
			} else {
				sb.append("; ");
			}
			sb.append(image);
		}
		images = sb.toString();
		
		return images;
	}

	public void setImages(String images) {
		this.images = images;
		
		if (images != null) {
			for (String image : images.split("; ")) {
				if (image.length() > 0) {
					addImageLocation(image);
				}
			}
		} else {
			this.images = "";
		}
	}

	@Override
	public String toString() {
		return String.format("(Announcement: id[%d] category[%s] title[%s])", id, category, title);
	}

	@Override
	public int compareTo(Announcement other) {
		if (this.getCreationDate().before(other.getCreationDate())) {
			return -1;
		} else if (this.getCreationDate().after(other.getCreationDate())) {
			return 1;
		}
		
		if (this.getId() < other.getId()) {
			return -1;
		} else if (this.getId() > other.getId()) {
			return 1;
		}
		return 0;
	}
}