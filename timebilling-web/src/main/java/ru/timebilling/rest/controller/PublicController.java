package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.model.service.ApplicationService;
import ru.timebilling.rest.domain.ApplicationRegistration;

@Controller
@RequestMapping("/public/api")
public class PublicController {
	
	@Autowired
	ApplicationService appService;
	
	@RequestMapping(value="/application", method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public ApplicationRegistration createApplication(@RequestBody ApplicationRegistration appRegData) 
			throws ApplicationException {    
		
		return appService.createApplication(appRegData);
	}


}
