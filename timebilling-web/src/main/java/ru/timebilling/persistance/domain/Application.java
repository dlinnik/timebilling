package ru.timebilling.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Application extends BaseEntity{
    
	@Column(name = "name", nullable = false, length = 64)
	String name;
	@Column(name = "screenname", nullable = false, length = 256)
	String screenName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	

}
