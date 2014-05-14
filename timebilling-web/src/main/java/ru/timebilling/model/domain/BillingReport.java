package ru.timebilling.model.domain;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BillingReport extends AppAwareBaseEntity{
	
	@Column(name = "startdate")
	private Date startDate;
	
	@Column(name = "enddate")
	private Date endDate;
	
	@Column(name = "creationdate")
	private Date creationDate;
	  
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
	@JsonBackReference
	private Set<Service> serviceList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
	@JsonBackReference
	private Set<Expense> expenseList;
	  
    @OneToOne	
	private Project project;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Set<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public Set<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(Set<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
    
    




}
