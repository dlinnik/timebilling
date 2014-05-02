package ru.timebilling.rest.domain;

public class UserDetails {
	
	private String username;
	private String email;
	private String appScreenName;
    private Integer role;
	
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAppScreenName() {
		return appScreenName;
	}
	public void setAppScreenName(String appScreenName) {
		this.appScreenName = appScreenName;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
    
    



}
