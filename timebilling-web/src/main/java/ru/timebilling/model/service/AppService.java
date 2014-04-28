package ru.timebilling.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.model.AppNameSupplier;
import ru.timebilling.model.repository.ApplicationRepository;
import ru.timebilling.web.component.AppContext;

@Service
public class AppService {
	
	static final Logger logger = LoggerFactory.getLogger(AppService.class);

	
    @Autowired
	ApplicationRepository reposittory;
    
    @Autowired
    AppContext appContext;
    
    public AppContext getApplicationContext(String appName){
    	
    	if(appContext.getApplication() == null){
    		//вычитываем данные о приложении из БД
    		appContext.setApplication(reposittory.findByName(appName));
            logger.debug("appcontext = [" + appContext.getApplication().getName() + "][" + appContext.getApplication().getScreenName() + "]");
    	}
    	if(appContext.getApplication()!=null){    		
	    	//всегда обновляем данные о приложении в статическом контексте (для multitentancy поддержки на уровне JPA)
	    	AppNameSupplier.setAppName(appContext.getApplication().getName());
    	}
    	
    	return appContext;
    }
    
    
    public AppContext getCurrentApplicationContext(){    	
    	if(appContext.getApplication()!=null){
    		
            logger.info("appcontext = [" + appContext.getApplication().getName() + "]");

	    	//всегда обновляем данные о приложении в статическом контексте (для multitentancy поддержки на уровне JPA)
	    	AppNameSupplier.setAppName(appContext.getApplication().getName());
    	}    	
    	return appContext;    	
    }
    

}
