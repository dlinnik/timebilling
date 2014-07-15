package ru.timebilling.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.model.AppNameSupplier;
import ru.timebilling.model.RequestURLSupplier;
import ru.timebilling.model.UserPassCredentials;
import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.User;
import ru.timebilling.model.domain.UserRoleEnum;
import ru.timebilling.model.repository.ApplicationRepository;
import ru.timebilling.rest.domain.ApplicationRegistration;
import static ru.timebilling.model.RequestURLSupplier.URL_LOGIN;
import static ru.timebilling.model.RequestURLSupplier.URL_LOGIN_UT;


@Service
public class ApplicationService {
	@Autowired
	ApplicationRepository appRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	public static final String VALIDATION_ERROR_APP_EXISTS = 
			"Приложение с данным именем уже существует";


	
	public ApplicationRegistration createApplication(ApplicationRegistration appData) throws ApplicationException{
		
		//check if already exists
		Application ae = appRepository.findByName(appData.getName());
		if(ae!=null){
			throw new ApplicationException(VALIDATION_ERROR_APP_EXISTS);
		}
		
		Application app = new Application();
		app.setName(appData.getName());
		app.setScreenName(appData.getScreenName());		
		app = appRepository.save(app);
		
		//эмулируем вход в созданное приложение для корректного создания пользователя
		AppNameSupplier.setAppName(app.getName());
		User user = userDetailsService.createNewUser(appData.getUserName(), 
				appData.getEmail(), UserRoleEnum.ROLE_ADMIN);
		
		UserPassCredentials up = new UserPassCredentials(user.getEmail(), user.getPassword());
		
		appData.setAppUrl(new StringBuilder(RequestURLSupplier.getBaseURL()).
				append(URL_LOGIN).append("?").
				append(URL_LOGIN_UT).append("=").
				append(UtUtils.encodeUserPassCredentials(up)).toString());
		
		return appData;
	}
	
	public String suggestAppNameByScreenName(String screenName){
		String appName = AppNameUtils.prepareAppName(screenName);
		if(appName.length() == 0){
			return appName;
		}
		
		int ind = 0;
		String appNameNext = appName;
		
		while(appRepository.findByName(appNameNext) != null){			
			appNameNext = appName + ++ind;
		}
		
		return appNameNext;
	}

}
