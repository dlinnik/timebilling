package ru.timebilling.model.service;

import java.util.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.BillingReportRepository;
import ru.timebilling.model.repository.ExpenseRepository;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.repository.ServiceRepository;
import ru.timebilling.model.service.conversion.ConversionUtils;

@Service
public class BillingService {
	
	static final Logger logger = LoggerFactory.getLogger(BillingService.class);
	
	@Autowired
	BillingReportRepository billingReportRepository;

	@Autowired
	ExpenseRepository expenseRepository;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ProjectRepository projectsRepository;
	
	public BillingReport create(Long projectId,  Date fromDate,  Date toDate){
		
		BillingReport report = new BillingReport();
		java.sql.Date start = ConversionUtils.convertToSQLDate(fromDate);
		java.sql.Date end = ConversionUtils.convertToSQLDate(toDate);
		
		report.setCreationDate(ConversionUtils.convertToSQLDate(Calendar.getInstance().getTime()));
		report.setStartDate(start);
		report.setEndDate(end);
		Project project = projectsRepository.findOne(projectId);
		report.setProject(project);
		
		report = billingReportRepository.save(report);
		
		//вычисляем услуги и затраты для попадания в отчет
		//TODO: оптимизировать (помянять на UPDATE)
		
		Iterable<ru.timebilling.model.domain.Service> services = serviceRepository.findToBill(projectId, 
				start, end);		
		for(ru.timebilling.model.domain.Service e : services){
			logger.debug("bill service [" + e.getId() + "]");
			e.setReport(report);
			serviceRepository.save(e);
		}

		Iterable<Expense> expenses = expenseRepository.findToBill(projectId, 
				start, end);		
		for(Expense e : expenses){
			logger.debug("bill expense [" + e.getId() + "]");
			e.setReport(report);
			expenseRepository.save(e);
		}
		
		
		return report;		
	}
	
	public Page<BillingReport> getAllReports(Pageable pageable){
		
		return billingReportRepository.findAll(pageable);
		
	}

	public void delete(Long id) {
		billingReportRepository.delete(id);
	}



}
