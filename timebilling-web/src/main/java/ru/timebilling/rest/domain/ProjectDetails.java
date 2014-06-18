package ru.timebilling.rest.domain;

import java.io.Serializable;
import java.util.Set;

public class ProjectDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private String client;

	
	Set<UserInProject> assignments;
	
	public ProjectDetails(){
		
	}
	
	public ProjectDetails(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Long getId(){
		return id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserInProject> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<UserInProject> assignments) {
		this.assignments = assignments;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	
	
}