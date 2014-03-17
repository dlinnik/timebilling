package ru.timebilling.web.filter;

import java.io.IOException;
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

import ru.timebilling.web.component.AppId;

public class AppIdFromSubdomainFilter implements Filter{
	
    static final Logger logger = LoggerFactory.getLogger(AppIdFromSubdomainFilter.class);
	
	AppId appId;

	public AppId getAppId() {
		return appId;
	}


	public void setAppId(AppId appId) {
		this.appId = appId;
	}
	
	@Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
    		throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        
        String domain = new URL(request.getRequestURL().toString()).getHost();
        String appId = null;
        if(domain.indexOf(".")!=-1){
        	appId = domain.substring(0, domain.indexOf("."));
        }
        logger.info("appId = [" + appId + "] from hostname [" + domain + "]");
        
        if(appId!=null){
        	this.appId.setId(appId);
        }
        chain.doFilter(req, res);

    }
    
    @Override
    public void destroy() {
        //
    }
}
