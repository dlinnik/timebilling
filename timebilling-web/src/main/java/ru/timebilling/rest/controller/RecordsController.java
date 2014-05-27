package ru.timebilling.rest.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.service.ApplicationException;
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
			Model model, 
			@RequestParam(value = "project") Long projectId,
			@RequestParam(value = "report", required = false) Long reportId,
			@PageableDefault(value = 5,  sort = "date", direction = Direction.DESC) Pageable pageable)
	{
		if(reportId!=null){
			return projectServicesService.getServicesByReport(reportId, pageable);
		}
		return projectServicesService.getServices(projectId, pageable);
	}
	
	@RequestMapping(value="/expenses", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Record> allExpensesForProject(
			Model model, @RequestParam("project") Long projectId, 
			@RequestParam(value = "report", required = false) Long reportId,
			@PageableDefault(value = 5, sort = "date", direction = Direction.DESC) Pageable pageable)
	{
		if(reportId!=null){
			return projectExpensesService.getExpensesByReport(reportId, pageable);			
		}
		return projectExpensesService.getExpenses(projectId, pageable);
	}
	
	@RequestMapping(value="service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public Record createService(@RequestBody Record record) throws ParseException, ApplicationException {    
		return projectServicesService.save(record);
	}

	@RequestMapping(value="service", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public Record updateService( @RequestBody Record record) throws ParseException, ApplicationException {    
		return projectServicesService.save(record);
	}

	@RequestMapping(value="service", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteService(@RequestParam("id") Long id) throws ParseException {    
		projectServicesService.delete(id);
	}
	
	
	@RequestMapping(value="expense", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public Record createExpense(@RequestBody Record record) throws ParseException, ApplicationException {    
		return projectExpensesService.save(record);
	}

	@RequestMapping(value="expense", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public Record updateExpense( @RequestBody Record record) throws ParseException, ApplicationException {    
		return projectExpensesService.save(record);
	}
	
	@RequestMapping(value="expense", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteExpense(@RequestParam("id") Long id) throws ParseException {    
		projectExpensesService.delete(id);
	}


	
}
