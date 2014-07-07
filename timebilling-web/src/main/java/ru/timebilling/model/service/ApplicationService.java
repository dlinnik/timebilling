package ru.timebilling.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.model.AppNameSupplier;
import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.UserRoleEnum;
import ru.timebilling.model.repository.ApplicationRepository;
import ru.timebilling.rest.domain.ApplicationRegistration;

@Service
public class ApplicationService {
	@Autowired
	ApplicationRepository appRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	
	public ApplicationRegistration createApplication(ApplicationRegistration appData){
		Application app = new Application();
		app.setName(appData.getName());
		app.setScreenName(appData.getScreenName());		
		app = appRepository.save(app);
		
		//эмулируем вход в созданное приложение для корректного создания пользователя
		AppNameSupplier.setAppName(app.getName());
		userDetailsService.createNewUser(appData.getUserName(), 
				appData.getEmail(), UserRoleEnum.ROLE_ADMIN);
		
		//TODO: сгенерировать уникальный (временный) линк, позволяющий перейти в приложение без ввода пароля
		appData.setAppUrl(appData.getName() + ".localhost:8080");
		
		return appData;
	}

}
