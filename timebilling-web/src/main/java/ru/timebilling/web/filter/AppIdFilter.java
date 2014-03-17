package ru.timebilling.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ru.timebilling.web.component.AppId;

/**
 * Фильтр, регистрируется в {@link HttpSecurity}. Определяет {@link AppId} на основе адресной строки
 * TODO: реализовать получение AppId из СУБД
 * 
 * @author vshmelev
 *
 */
public class AppIdFilter implements Filter{
	
	AppId appId;

	
	
	
    public AppId getAppId() {
		return appId;
	}


	public void setAppId(AppId appId) {
		this.appId = appId;
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
        	this.appId.setId(appId);
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
