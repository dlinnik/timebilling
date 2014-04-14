package ru.timebilling.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ru.timebilling.core.domain.ProjectDetails;

@Entity
public class Project extends AppAwareBaseEntity{

	@Column(name = "name", nullable = false, length = 256)
	private String name;
	@Column(name = "description", nullable = true, length = 1024)
	private String description;

	@Column(name = "client", nullable = true, length = 1024)
	private String client;
	
	
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
	
	
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public static Project fromProjectDetails(ProjectDetails d){
		Project p = new Project();
		p.name = d.getName();
		p.description = d.getDescription();
		p.id = d.getId();
		return p;
	}
	


}
