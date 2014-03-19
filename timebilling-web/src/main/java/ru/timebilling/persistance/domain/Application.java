package ru.timebilling.persistance.domain;

import javax.persistence.Entity;

@Entity
public class Application extends BaseEntity{
	
	String name;
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
