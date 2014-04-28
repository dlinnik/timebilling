package ru.timebilling.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

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


	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

}
