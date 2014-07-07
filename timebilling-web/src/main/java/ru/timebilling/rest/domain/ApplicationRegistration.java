package ru.timebilling.rest.domain;

import java.io.Serializable;

public class ApplicationRegistration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String name;

	String screenName;
	
	String userName;
	
	String email;
	
	String appUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	
	

	

}
