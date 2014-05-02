package ru.timebilling.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.User;
import ru.timebilling.rest.domain.UserDetails;

/**
 * Вспомогательные операции
 * @author vshmelev
 *
 */
@Controller
public class MiscController extends BaseAPIController{
    @RequestMapping("/current_user")
    @ResponseBody
	public UserDetails getCurrentUserDetails(){
    	UserDetails ret = new UserDetails();
		User user = userInSession.getCurrentUser();
		Integer role = user.getRole().getRole();
		Application app = appContext.getApplication();
		
		ret.setAppScreenName(app.getScreenName());
		ret.setRole(role);
		ret.setUsername(user.getUsername());
		ret.setEmail(user.getEmail());
		
		return ret;
	}

}
