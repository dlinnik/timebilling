package ru.timebilling.persistance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ru.timebilling.core.domain.ProjectDetails;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
	private String name;
	private String description;
	
	
	public Project() {
		super();
	}

	public Project(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public ProjectDetails toProjectDetails(){
		return new ProjectDetails(name, description);				
	}
	
	public static Project fromProjectDetails(ProjectDetails d){
		Project p = new Project();
		p.name = d.getName();
		p.description = d.getDescription();
		return p;
	}
	


}
