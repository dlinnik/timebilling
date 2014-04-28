package ru.timebilling.web.component;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ru.timebilling.model.domain.Application;

/**
 * Содержит информацию о текущем приложении в сессии.
 * @author vshmelev
 *
 */
@Component("appContext") 
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppContext {
	
	private Application application;

	
	public AppContext() {
		super();
	}

	public AppContext(Application application) {
		super();
		this.application = application;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	

}
