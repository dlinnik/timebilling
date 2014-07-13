package ru.timebilling.model;

/**
 * internal credentials class, not for usage on UI
 * @author vshmelev
 *
 */
public class UserPassCredentials {
	
	private transient String userName;
	private transient String password;
		
	public UserPassCredentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	

}
