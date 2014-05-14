package ru.timebilling.rest.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.service.BillingService;
import ru.timebilling.model.service.UserDetailsServiceImpl;
import ru.timebilling.rest.domain.Billing;
import ru.timebilling.rest.domain.BillingGroupBy;
import ru.timebilling.rest.domain.BillingItem;
import static ru.timebilling.rest.domain.BillingGroupBy.monthly;
import static ru.timebilling.rest.domain.BillingGroupBy.quarterly;

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
    		@RequestParam(value="from", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
    		@RequestParam(value="to", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, 
    		@RequestParam(value="project", required=false) Long projectId,
    		@RequestParam(value="group_by", required=false, defaultValue="none") BillingGroupBy groupBy) {
    	
    	Billing result = null;
    	logger.info("request billing from [" + fromDate + "] to [" + toDate + "] by project [" + projectId + "] group by [" + groupBy + "]");
    	
    	switch (groupBy) {
		case monthly:
			
//			break;

		case quarterly:
			
//			break;
			
		default:
			result = getBilling(fromDate, toDate, projectId, groupBy);
		}
    	return result;
    }
    
    @RequestMapping(value="/billing", method=RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BillingReport createBillingReport(
    		@RequestParam(value="from", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
    		@RequestParam(value="to", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, 
    		@RequestParam(value="project", required=true) Long projectId) {
    	
    	return billingService.create(projectId, fromDate, toDate);
    	
    }


	private Billing getBilling(Date fromDate, Date toDate,
			Long projectId, BillingGroupBy groupBy) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<BillingItem> items = new ArrayList<BillingItem>();
		try{
			BillingItem item = new BillingItem(projectsRepository.findOne(1L), 
					df.parse("2014-03-01"), df.parse("2014-03-31"), new Float(1000), new Float(2000));
			items.add(item);
			
			item = new BillingItem(projectsRepository.findOne(1L), 
					df.parse("2014-02-01"), df.parse("2014-02-28"), new Float(3000), new Float(1000));
			items.add(item);

			item = new BillingItem(projectsRepository.findOne(2L), 
					df.parse("2014-03-01"), df.parse("2014-03-15"), new Float(500), new Float(4000));
			items.add(item);
			
		}catch(ParseException e){
			logger.error("", e);
		}
		
		return new Billing(fromDate, toDate, items);
	}
    	    	
    	


}
