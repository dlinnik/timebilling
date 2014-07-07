package ru.timebilling.web.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.timebilling.model.service.AppService;
import ru.timebilling.web.component.AppContext;

@Component
public class AppIdFromSubdomainFilter implements Filter{
	
    static final Logger logger = LoggerFactory.getLogger(AppIdFromSubdomainFilter.class);
	
    @Autowired
    AppService appService;
	
	@Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

	
	
    public AppService getAppService() {
		return appService;
	}



	public void setAppService(AppService appService) {
		this.appService = appService;
	}



	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
    		throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        logger.info(request.getRequestURI());
        //TODO: нужен более изящный способ отключить этот фильтр для public api
        if(!request.getRequestURI().startsWith("/public")){
        
	        String appId = getAppNameFromRequest(request);
	        
	        AppContext appContext = appService.getApplicationContext(appId);
	        
	        if(appContext.getApplication() == null){
	        	//приложение не найдено, TODO: нужен редирект на правильную страницу
	        	
	        	req.getRequestDispatcher("/403").forward(req, res);
/*	        	
	        	HttpServletResponse httpResponse = (HttpServletResponse) res;
	        	httpResponse.sendRedirect("/403");
*/	        	return;
	        }
        }
        
        chain.doFilter(req, res);

    }
    
    @Override
    public void destroy() {
        //
    }
    
	public String getAppNameFromRequest(HttpServletRequest request) throws MalformedURLException{
        String domain = new URL(request.getRequestURL().toString()).getHost();
        String appId = null;
        if(domain.indexOf(".")!=-1){
        	appId = domain.substring(0, domain.indexOf("."));
        }
        logger.debug("appName = [" + appId + "] from hostname [" + domain + "] for uri [" + request.getRequestURL().toString() + "]");
        
        return appId;
	}

}
