package ru.timebilling.model.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Назначение сотрудника на проект
 * @author vshmelev
 *
 */
@Entity
public class Assignment extends AppAwareBaseEntity{
	
	@ManyToOne
//	@JsonBackReference
    @JsonManagedReference	
	private User user;
	
	@ManyToOne
//	@JsonBackReference
    @JsonManagedReference
	private Project project;
	
	@Column(name = "rate")
	private BigDecimal rate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}	
}
