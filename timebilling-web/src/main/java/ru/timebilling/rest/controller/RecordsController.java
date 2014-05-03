package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.service.ProjectExpensesService;
import ru.timebilling.model.service.ProjectServicesService;
import ru.timebilling.rest.domain.Record;

@Controller
public class RecordsController extends BaseAPIController{

	@Autowired
	ProjectServicesService projectServicesService;
	
	@Autowired
	ProjectExpensesService projectExpensesService;
	
	@RequestMapping(value="/services", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Record> allServicesForProject(
			Model model, @RequestParam("project") Long projectId, 
			@PageableDefault(value = 5) Pageable pageable)
	{
		return projectServicesService.getServices(projectId, pageable);
	}

	@RequestMapping(value="/expenses", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Record> allExpensesForProject(
			Model model, @RequestParam("project") Long projectId, 
			@PageableDefault(value = 5) Pageable pageable)
	{
		return projectExpensesService.getExpenses(projectId, pageable);
	}
	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public String servicesTable(Model model, @RequestParam("project") Long projectId, Pageable pageable) {
//		model.addAttribute("services", projectServicesService.getServices(projectId, pageable));
//		return "tables :: servicesTableFragment";
//	}
	
//	@RequestMapping(value="", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
//	@ResponseBody
//	public Record create( @RequestBody Record serviceDetails) throws ParseException {    
//		return projectServicesService.save(serviceDetails);
//	}
//
//	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
//	@ResponseBody
//	public Record update( @RequestBody Record serviceDetails) throws ParseException {    
//		return projectServicesService.save(serviceDetails);
//	}
	
}
