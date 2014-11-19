package com.bielu.annoboard.action.common;

import com.opensymphony.xwork2.ActionSupport;

public class DispatcherAction extends ActionSupport {

	private static final long serialVersionUID = 8461121162041685834L;

	private String result = "/main.jsp";
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
