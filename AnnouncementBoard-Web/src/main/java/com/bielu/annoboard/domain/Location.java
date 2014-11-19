package com.bielu.annoboard.domain;

import java.util.List;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Indexed(index = "location")
public class Location implements Identifiable {

	private static final long serialVersionUID = 5332684126310410654L;

	private String countryCode;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String city;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String district;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String address;
	
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String postalCode;
	
	private double latitude;
	
	private double longitude;
	
	private String phoneNumbers;
	
	private List<String> phoneNumbersList;
	
	@ContainedIn
	private Announcement announcement;
	
	@DocumentId
	private long id;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@RequiredStringValidator(message = "validation.empty.city", key = "validation.empty.city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@FieldExpressionValidator(
			expression = "longitude != 0.0d || latitude != 0.0d", 
			message = "validation.invalid.longitude", 
			key = "validation.invalid.longitude",
			shortCircuit = true
	)
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<String> getPhoneNumbersList() {
		return phoneNumbersList;
	}

	public void setPhoneNumbersList(List<String> phoneNumbersList) {
		this.phoneNumbersList = phoneNumbersList;
	}

	public String getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
	
	@Override
	public String toString() {
		return String.format("(Location: id[%d] address[%s] city[%s] country[%s] lat[%f] lng[%f])", 
				id, address, city, countryCode, latitude, longitude);
	}
}
