package ru.timebilling.model.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Project extends AppAwareBaseEntity {

	@Column(name = "name", nullable = false, length = 256)
	private String name;
	@Column(name = "description", nullable = true, length = 1024)
	private String description;

	@Column(name = "client", nullable = true, length = 1024)
	private String client;

	@Formula("(select count(distinct a.user_id) from assignment a where a.project_id = id)")
	private int size;

	@Formula("(select sum(s.spentTime) from service s where s.project_id = id)")
	private BigDecimal totalTime;

	@Formula("(select sum(s.spentMoney) from expense s where s.project_id = id)")
	private BigDecimal totalMoney;
	
	//TODO: implement me!
	@Formula("(select 2)")
	private int reportsCount;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    //@JsonManagedReference
	@JsonBackReference
	private Set<Assignment> assignments;

	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public Project() {
		super();
	}

	public Project(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getSize() {
		return size;
	}

	public BigDecimal getTotalTime() {
		return totalTime;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	
	public int getReportsCount(){
		return reportsCount;
	}

}
