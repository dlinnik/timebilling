package ru.timebilling.core.domain;

import java.io.Serializable;

public class ServiceDetails implements Serializable{
	
	

//	public ServiceDetails(Long id, String day, String month, String year,
//			String spentTime, Long user, Long project, String comment) {
//		super();
//		this.id = id;
//		this.day = day;
//		this.month = month;
//		this.year = year;
//		this.spentTime = spentTime;
//		this.user = user;
//		this.project = project;
//		this.comment = comment;
//	}
//	
//	
//
//	public ServiceDetails() {
//		super();
//	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String day;
	private String month;
	private String year;
	private String date;
	  
	private String spentTime;
	  
	private Long user;
	private String userScreenName;
	
	private Long project;
	
	private String comment;
	
	private boolean editable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(String spentTime) {
		this.spentTime = spentTime;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
