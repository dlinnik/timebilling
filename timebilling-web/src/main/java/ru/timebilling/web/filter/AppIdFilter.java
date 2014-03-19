package ru.timebilling.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ru.timebilling.service.AppService;
import ru.timebilling.web.component.AppContext;

/**
 * Фильтр, регистрируется в {@link HttpSecurity}. Определяет {@link AppContext} на основе адресной строки
 * TODO: реализовать получение AppId из СУБД
 * 
 * @author vshmelev
 *
 */
public class AppIdFilter implements Filter{
	
	AppService appService;
	
	
	
	public AppService getAppService() {
		return appService;
	}


	public void setAppService(AppService appService) {
		this.appService = appService;
	}


	public String parseAppIdFromUrl(String requestURI){
		String appId = null;
		if (requestURI.startsWith("/app/")) {
        	
            String str = requestURI.substring("/app/".length()); //cut /app/ prefix
            
        	if(!str.contains("/")){
        		//add "/" to appId
        		str = str + "/";
        	}
            appId = str.substring(0, str.indexOf("/"));	// get app id
		}
		return appId;
	}


	@Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
    		throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        
        String appId = parseAppIdFromUrl(requestURI);
        if(appId!=null){
        	appService.getApplicationContext(appId);
        }

        
        System.out.println("AppIdFilter: REQUEST Intercepted for URI: "
                + requestURI);
        
        chain.doFilter(req, res);


    }
    
    @Override
    public void destroy() {
        //
    }

}
