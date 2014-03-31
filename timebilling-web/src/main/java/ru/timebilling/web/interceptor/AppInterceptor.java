package ru.timebilling.web.interceptor;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ru.timebilling.service.AppService;
import ru.timebilling.web.component.AppContext;
 
@Component
public class AppInterceptor extends HandlerInterceptorAdapter {
	static final Logger logger = LoggerFactory.getLogger(AppInterceptor.class);
	
	@Autowired
	AppService appService;


	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("INTERCEPT REQUEST [" + request.getRequestURI() + "]");
		
        String appId = getAppNameFromRequest(request);

        AppContext appContext = appService.getApplicationContext(appId);
        
        if(appContext.getApplication() == null){
        	//приложение не найдено, TODO: нужен редирект на правильную страницу
        	
        	request.getRequestDispatcher("/403").forward(request, response);
/*	        	
        	response.sendRedirect("/403");
*/	        	return false;
        }
        
		return true;
	  
	  }
	
	public String getAppNameFromRequest(HttpServletRequest request) throws MalformedURLException{
        String domain = new URL(request.getRequestURL().toString()).getHost();
        String appId = null;
        if(domain.indexOf(".")!=-1){
        	appId = domain.substring(0, domain.indexOf("."));
        }
        logger.info("appName = [" + appId + "] from hostname [" + domain + "]");
        
        return appId;
	}
}
