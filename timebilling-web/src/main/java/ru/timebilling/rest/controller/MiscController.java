package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.User;
import ru.timebilling.model.service.conversion.UserDetailsConverter;
import ru.timebilling.rest.domain.UserDetails;

/**
 * Вспомогательные операции
 * @author vshmelev
 *
 */
@Controller
public class MiscController extends BaseAPIController{
	
	@Autowired
	UserDetailsConverter converter;
	
    @RequestMapping("/current_user")
    @ResponseBody
	public UserDetails getCurrentUserDetails(){
    	return converter.toDTO(userInSession.getCurrentUser());
	}

}
