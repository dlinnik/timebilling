package ru.timebilling.persistance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ru.timebilling.core.domain.ProjectDetails;

@Entity
public class Project extends BaseEntity{

	private String name;
	private String description;
	
	
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

	public ProjectDetails toProjectDetails(){
		return new ProjectDetails(id, name, description);				
	}
	
	public static Project fromProjectDetails(ProjectDetails d){
		Project p = new Project();
		p.name = d.getName();
		p.description = d.getDescription();
		p.id = d.getId();
		return p;
	}
	


}