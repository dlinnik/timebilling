package ru.timebilling.rest.controller;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.model.service.BillingService;
import ru.timebilling.rest.domain.Billing;
import ru.timebilling.rest.domain.BillingGroupBy;

@Controller
public class BillingController extends BaseAPIController{
	
	static final Logger logger = LoggerFactory.getLogger(BillingController.class);

	@Autowired
	BillingService billingService;
	
	@Autowired
	ProjectRepository projectsRepository;
	
    @RequestMapping(value="/billing", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Billing billing(
    		//ignored for Beta
    		@RequestParam(value="from", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
    		//ignored for Beta
    		@RequestParam(value="to", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, 
    		@RequestParam(value="project", required=false) Long projectId,
    		//ignored for Beta
    		@RequestParam(value="group_by", required=false, defaultValue="monthly") BillingGroupBy groupBy) {
    	
    	logger.info("request billing from [" + fromDate + "] to [" + toDate + "] by project [" + projectId + "] group by [" + groupBy + "]");
    	
    	return billingService.getBilling(fromDate, toDate, projectId, groupBy);
    }
    
    @RequestMapping(value="/billing/report", method=RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BillingReport createBillingReport(
    		@RequestParam(value="from", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
    		@RequestParam(value="to", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, 
    		@RequestParam(value="project", required=true) Long projectId) throws ApplicationException{
    	
    	return billingService.create(projectId, fromDate, toDate);
    }
    
    @RequestMapping(value="/billing/report/{reportId}", method=RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BillingReport updateBillingReport(@PathVariable("reportId") Long id,
    		@RequestParam(value="from", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
    		@RequestParam(value="to", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) throws ApplicationException{
    	
    	return billingService.update(id, fromDate, toDate);
    	
    }

    @RequestMapping(value="/billing/report/{reportId}/exclude/service/{recordId}", 
    		method=RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void excludeService(@PathVariable("reportId") Long reportId,
    		@PathVariable("recordId") Long recordId,
    		@RequestParam(value="exclude", required=true) Boolean exclude) {
    	
    	billingService.excludeServiceFromReport(reportId, recordId, exclude);    	
    }
    
    @RequestMapping(value="/billing/report/{reportId}/exclude/expense/{recordId}", 
    		method=RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void excludeExpense(@PathVariable("reportId") Long reportId,
    		@PathVariable("recordId") Long recordId,
    		@RequestParam(value="exclude", required=true) Boolean exclude) {
    	
    	billingService.excludeExpenseFromReport(reportId, recordId, exclude);
    	
    }
    
    
    
	@RequestMapping(value="/billing/reports", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<BillingReport> allExpensesForProject(
			@PageableDefault(value = 5, sort = "creationDate", direction = Direction.DESC) Pageable pageable)
	{
		return billingService.getAllReports(pageable);
	}
	
	@RequestMapping(value = "/billing/report/{reportId}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BillingReport report(Model model, @PathVariable("reportId") Long id) {
		return billingService.getReport(id);
	}

	

	@RequestMapping(value="/billing/report", method = RequestMethod.DELETE)
	@ResponseBody
	@Deprecated
	public void deleteBillingReport(@RequestParam("id") Long id) throws ParseException {    
		billingService.delete(id);
	}
	
	@RequestMapping(value="/billing/report/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteBillingReportById(@PathVariable("id") Long id) throws ParseException {    
		billingService.delete(id);
	}
	

	@RequestMapping(value="/billing/period", method = RequestMethod.GET)
	@ResponseBody
	public Date[] getBillingPeriod(@RequestParam(value="projectId", required=false) Long projectId){    
		return billingService.getAvailableRecordsPeriod(projectId);
	}

}
