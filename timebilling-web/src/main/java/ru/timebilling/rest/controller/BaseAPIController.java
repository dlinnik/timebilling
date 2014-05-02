package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.User;
import ru.timebilling.rest.domain.UserDetails;
import ru.timebilling.web.component.AppContext;
import ru.timebilling.web.component.UserSessionComponent;

@Controller
@RequestMapping("/api")
/**
 * Базовый абстрактный контроллер для REST API приложения
 * @author vshmelev
 *
 */
public abstract class BaseAPIController {
	
	@Autowired
	UserSessionComponent userInSession;

    @Autowired
    AppContext appContext;

	
}
