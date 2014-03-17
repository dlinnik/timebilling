package ru.timebilling.web.component;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("appId") 
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class AppId {
	
	private String id;

	
	public AppId() {
		super();
	}

	public AppId(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
