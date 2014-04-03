package ru.timebilling.rest.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.core.domain.ServiceDetails;
import ru.timebilling.service.ProjectServicesService;

@Controller
@RequestMapping(value="/services")
public class ServicesController {

	@Autowired
	ProjectServicesService projectServicesService;
	
//	@RequestMapping(value="", method=RequestMethod.GET,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Iterable<Service> allServices()
//	{
//		return servicesRepository.findAll();
//	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String servicesTable(Model model, @RequestParam("project") Long projectId) {
		model.addAttribute("services", projectServicesService.getServices(projectId));
		return "tables :: servicesTableFragment";
	}
	
	@RequestMapping(value="", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public ServiceDetails create( @RequestBody ServiceDetails serviceDetails) throws ParseException {    
		return projectServicesService.save(serviceDetails);
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public ServiceDetails update( @RequestBody ServiceDetails serviceDetails) throws ParseException {    
		return projectServicesService.save(serviceDetails);
	}
	
}
