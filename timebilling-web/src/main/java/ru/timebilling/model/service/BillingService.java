package ru.timebilling.model.service;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

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
	
    @PersistenceContext
    private EntityManager entityManager;

	
	public BillingReport create(Long projectId,  Date fromDate,  Date toDate){
		
		final BillingReport report = new BillingReport();
		java.sql.Date start = ConversionUtils.convertToSQLDate(fromDate);
		java.sql.Date end = ConversionUtils.convertToSQLDate(toDate);
		
		report.setCreationDate(ConversionUtils.convertToSQLDate(Calendar.getInstance().getTime()));
		report.setStartDate(start);
		report.setEndDate(end);
		Project project = projectsRepository.findOne(projectId);
		report.setProject(project);
		
		Iterable<ru.timebilling.model.domain.Service> services = serviceRepository.findToBill(projectId, 
				start, end);		
		Iterable<Expense> expenses = expenseRepository.findToBill(projectId, 
				start, end);		
		
		report.setServiceList(Sets.newHashSet(Iterables.transform(services, 
				new Function<ru.timebilling.model.domain.Service, ru.timebilling.model.domain.Service>(){

			@Override
			public ru.timebilling.model.domain.Service apply(ru.timebilling.model.domain.Service input) {
				input.setReport(report);				
				return input;
			}
			
		})));
		
		report.setExpenseList(Sets.newHashSet(Iterables.transform(expenses, 
				new Function<Expense, Expense>(){

			@Override
			public Expense apply(Expense input) {
				input.setReport(report);				
				return input;
			}
			
		})));

		BillingReport result = billingReportRepository.save(report);	
		//рефрешим объект для обновления калькулируемых полей
		entityManager.refresh(result);
		return result;
	}
	
	public Page<BillingReport> getAllReports(Pageable pageable){
		
		return billingReportRepository.findAll(pageable);
		
	}

	public void delete(Long id) {
		billingReportRepository.delete(id);
	}



}
