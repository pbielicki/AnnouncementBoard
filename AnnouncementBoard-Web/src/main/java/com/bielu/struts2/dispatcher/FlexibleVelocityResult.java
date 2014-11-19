package com.bielu.struts2.dispatcher;

import org.apache.struts2.dispatcher.VelocityResult;

public class FlexibleVelocityResult extends VelocityResult {

	private static final long serialVersionUID = -5665021488620737137L;

	private String contentType = "text/html";

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	protected String getContentType(String templateLocation) {
		return contentType;
	}
}
