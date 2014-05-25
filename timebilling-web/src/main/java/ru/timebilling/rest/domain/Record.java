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
	private Date date;
	  
	private BigDecimal value;
	  
	private Long user;
	private String userScreenName;
	
	private Long project;
	
	private String comment;
	
	private boolean auth;
	
	private boolean disable;
	
	private Boolean excludedFromReport;
	private Long report;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public Boolean isExcludedFromReport() {
		return excludedFromReport;
	}

	public void setExcludedFromReport(Boolean excludedFromReport) {
		this.excludedFromReport = excludedFromReport;
	}

	public Long getReport() {
		return report;
	}

	public void setReport(Long report) {
		this.report = report;
	}
	
	
	
	
	
	
	
}
