package ru.timebilling.web.interceptor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ru.timebilling.model.AppNameSupplier;
import ru.timebilling.web.component.UserSessionComponent;

//TODO: change to filter since MVC interceptor isn't supposed to handle all cases?
@Component
public class JPAFilterInterceptor extends HandlerInterceptorAdapter {
	static final Logger logger = LoggerFactory.getLogger(JPAFilterInterceptor.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private UserSessionComponent userSessionComponent;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String userName = "";
		if(userSessionComponent!=null && userSessionComponent.getCurrentUser()!=null && userSessionComponent.getCurrentUser().getUsername()!=null){
			userName = userSessionComponent.getCurrentUser().getUsername();
		}
		logger.info("intercept request to add filter tb_appNameFilter = [" + AppNameSupplier.getAppName() + "][" + 
				userName + "]");
		Session session = (Session)entityManager.getDelegate();
		
		org.hibernate.Filter filter = session.getEnabledFilter("tb_appNameFilter");
		if(filter == null){
			session.enableFilter("tb_appNameFilter").setParameter("appNameFilterString", AppNameSupplier.getAppName());
		}
		return true;
	}
	
	
}