package ru.timebilling.model.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Service extends AppAwareBaseEntity{

	@Column(name = "servicedate", nullable = false)
	private Date date;
	  
	@Column(name = "spenttime", nullable = false, scale = 2)
	private BigDecimal spentTime;
	  
	@ManyToOne
	private User employee;
	
	@ManyToOne
	private Project project;
	
	@Column(name = "comment", nullable = false, length = 1024)
	private String comment;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(BigDecimal spentTime) {
		this.spentTime = spentTime;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
