package ru.timebilling.model.service;

import java.util.Date;
import java.util.Calendar;
import java.util.Set;

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

import ru.timebilling.model.domain.BaseRecordEntity;
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
		
		//вычисляем услуги и затраты для попадания в отчет 
		//(условие: e.project.id = :projectId AND e.report IS NULL AND e.date >= :startDate AND e.date <= :endDate)
		
		Iterable<ru.timebilling.model.domain.Service> services = serviceRepository.findToBill(projectId, 
				start, end);		
		Iterable<Expense> expenses = expenseRepository.findToBill(projectId, 
				start, end);	
		
		//задаем отчет для записей
		prepareRecordsToReport(report, services);
		prepareRecordsToReport(report, expenses);

		
		//создаем отчет
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
	
	public BillingReport getReport(Long id){
		return billingReportRepository.findOne(id);
	}

	public BillingReport update(Long id, Date fromDate, Date toDate) {
		
		BillingReport report = billingReportRepository.findOne(id);

		if(report!=null){
						
			java.sql.Date start = ConversionUtils.convertToSQLDate(fromDate);
			java.sql.Date end = ConversionUtils.convertToSQLDate(toDate);
			
			//do only if at least one date changed			
			if(report.getStartDate().compareTo(start)!=0 || report.getEndDate().compareTo(end)!=0){
				report.setStartDate(start);
				report.setEndDate(end);
				
				//исключаем записи не попадающие больше в период
				removeRecordsFromReport(start, end, report.getServiceList());
				removeRecordsFromReport(start, end, report.getExpenseList());
				
				//находим записи которые теперь попадают в новый период
				Iterable<ru.timebilling.model.domain.Service> services = serviceRepository.findToBill(report.getProject().getId(), 
						start, end);					
				Iterable<Expense> expenses = expenseRepository.findToBill(report.getProject().getId(), 
						start, end);	
				prepareRecordsToReport(report, services);
				prepareRecordsToReport(report, expenses);
				
	
				report = billingReportRepository.save(report);	
				//рефрешим объект для обновления калькулируемых полей
				entityManager.refresh(report);
			}
		}
		
		return report;
	}
	
	public void excludeServiceFromReport(Long reportId, Long recordId, Boolean exclude){
		ru.timebilling.model.domain.Service record = serviceRepository.findOne(recordId);
		if(record!=null && 
				record.getReport()!=null && reportId.equals(record.getReport().getId())){
			record.setExcluded(exclude);
			serviceRepository.save(record);
		}
	}

	public void excludeExpenseFromReport(Long reportId, Long recordId, Boolean exclude){
		Expense record = expenseRepository.findOne(recordId);
		if(record!=null && 
				record.getReport()!=null && reportId.equals(record.getReport().getId())){
			record.setExcluded(exclude);
			expenseRepository.save(record);
		}
	}
	
	protected <T extends BaseRecordEntity> Set<T> prepareRecordsToReport(final BillingReport report, Iterable<T> t){
		return Sets.newHashSet(Iterables.transform(t, 
				new Function<T, T>(){
			@Override
			public T apply(T input) {
				input.setReport(report);				
				return input;
			}
		}));
	}
	
	protected <T extends BaseRecordEntity> Set<T> removeRecordsFromReport(final java.sql.Date from, final java.sql.Date to, Iterable<T> t){
		return Sets.newHashSet(Iterables.transform(t, 
				new Function<T, T>(){
			@Override
			public T apply(T input) {
				if(input.getDate().compareTo(from) < 0 || input.getDate().compareTo(to) > 0){					
					input.setReport(null);
					input.setExcluded(false);
				}
				return input;
			}
		}));
	}
	
}
