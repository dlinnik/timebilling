package ru.timebilling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.persistance.AppNameSupplier;
import ru.timebilling.persistance.repository.ApplicationRepository;
import ru.timebilling.web.component.AppContext;

@Service
public class AppService {
	
    @Autowired
	ApplicationRepository reposittory;
    
    @Autowired
    AppContext appContext;
    
    public AppContext getApplicationContext(String appName){
    	
    	if(appContext.getApplication() == null){
    		//вычитываем данные о приложении из БД
    		appContext.setApplication(reposittory.findByName(appName));
    	}
    	if(appContext.getApplication()!=null){
	    	//всегда обновляем данные о приложении в статическом контексте (для multitentancy поддержки на уровне JPA)
	    	AppNameSupplier.setAppName(appContext.getApplication().getName());
    	}
    	
    	return appContext;
    }
    

}
