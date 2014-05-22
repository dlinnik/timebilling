package ru.timebilling.model.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Базовая сущность для записей услуг и расходов
 * @author vshmelev
 *
 */
@MappedSuperclass
public class BaseRecordEntity extends AppAwareBaseEntity{
	
	@Column(name = "date")
	private Date date;

	@ManyToOne
	private User employee;
	
	@ManyToOne
	private Project project;
	
	@Column(name = "comment", nullable = false, length = 1024)
	private String comment;
	
	@ManyToOne
	private BillingReport report;
	
	@Column(name = "excluded")
    private Boolean excluded = Boolean.FALSE;



	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public BillingReport getReport() {
		return report;
	}

	public void setReport(BillingReport report) {
		this.report = report;
	}

	public Boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}
	
	
	
	


}
