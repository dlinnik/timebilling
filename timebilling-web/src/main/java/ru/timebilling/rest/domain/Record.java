package ru.timebilling.rest.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Record implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
//	private String day;
//	private String month;
//	private String year;
	private Date date;
	  
	private BigDecimal value;
	  
	private Long user;
	private String userScreenName;
	
	private Long project;
	
	private String comment;
	
	private boolean auth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getDay() {
//		return day;
//	}
//
//	public void setDay(String day) {
//		this.day = day;
//	}
//
//	public String getMonth() {
//		return month;
//	}
//
//	public void setMonth(String month) {
//		this.month = month;
//	}
//
//	public String getYear() {
//		return year;
//	}
//
//	public void setYear(String year) {
//		this.year = year;
//	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	
	public boolean isNew(){
		return getId() == null;
	}
	
}
