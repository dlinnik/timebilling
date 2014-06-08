package ru.timebilling.model.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
import ru.timebilling.rest.domain.Billing;
import ru.timebilling.rest.domain.BillingGroupBy;
import ru.timebilling.rest.domain.BillingItem;
import static ru.timebilling.model.service.conversion.ConversionUtils.*;

@Service
public class BillingService {
	
	public static final String VALIDATION_ERROR_REPORTS_PERIOD_CONFLICT = 
			"Период отчета пересекается с созданным ранее отчетом";
	
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
    
	public Billing getBilling(Date fromDate, Date toDate,
			Long projectId, BillingGroupBy groupBy) {
		
		List<Object[]> billingServices = null;
		List<Object[]> billingExpenses = null;
		
		if(projectId!=null){
			billingServices = serviceRepository.billingByProject(projectId);
			billingExpenses = expenseRepository.billingByProject(projectId);
		}else{
			billingServices = serviceRepository.billing();
			billingExpenses = expenseRepository.billing();
		}
		
		Billing result = new Billing();
	
		for(Object[] o : billingServices){
			if(o[0] != null && o[2] != null && o[3] != null) //на всякий случай
				result.add((Project)o[0], 
						o[1] == null ? BigDecimal.ZERO.floatValue() : ((BigDecimal)o[1]).floatValue(),
								(Integer)o[2], (Integer)o[3], Billing.BillingType.TIME);
		}

		for(Object[] o : billingExpenses){
			if(o[0] != null && o[2] != null && o[3] != null) //на всякий случай
				result.add((Project)o[0], 
						o[1] == null ? BigDecimal.ZERO.floatValue() : ((BigDecimal)o[1]).floatValue(),
								(Integer)o[2], (Integer)o[3], Billing.BillingType.EXP);
		}		
		return result;
	}


	
	public BillingReport create(Long projectId,  Date fromDate,  Date toDate) throws ApplicationException{
		
		final BillingReport report = new BillingReport();
		java.sql.Date start = convertToSQLDate(fromDate);
		java.sql.Date end = convertToSQLDate(toDate);
		
		report.setCreationDate(convertToSQLDate(Calendar.getInstance().getTime()));
		report.setStartDate(start);
		report.setEndDate(end);
		Project project = projectsRepository.findOne(projectId);
		report.setProject(project);
		
		validate(report);
		
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
	
	protected void validate(BillingReport report) throws ApplicationException{
		Iterable<BillingReport> existingReports = billingReportRepository.findByProjectAndPeriod(
				report.getProject().getId(), report.getStartDate(), report.getEndDate());
		Iterator<BillingReport> all = existingReports.iterator();
		
		if(report.isNew() && all.hasNext()){
			throw new ApplicationException(VALIDATION_ERROR_REPORTS_PERIOD_CONFLICT);
		}
		
		while (all.hasNext()) {
			BillingReport r = all.next();
			if(!r.getId().equals(report.getId())){
				throw new ApplicationException(VALIDATION_ERROR_REPORTS_PERIOD_CONFLICT);
			}
			
		}
		
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

	public BillingReport update(Long id, Date fromDate, Date toDate) throws ApplicationException{
		
		BillingReport report = billingReportRepository.findOne(id);

		if(report!=null){
						
			java.sql.Date start = convertToSQLDate(fromDate);
			java.sql.Date end = convertToSQLDate(toDate);
			
			//do only if at least one date changed			
			if(report.getStartDate().compareTo(start)!=0 || report.getEndDate().compareTo(end)!=0){
				report.setStartDate(start);
				report.setEndDate(end);

				validate(report);

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
	
	public java.sql.Date[] getAvailableRecordsPeriod(Long projectId){
		Object[] servicesPeriod;
		Object[] expensesPeriod;

		if(projectId == null){
			servicesPeriod = serviceRepository.findPeriod().get(0);
			expensesPeriod = expenseRepository.findPeriod().get(0);

		}else{
			servicesPeriod = serviceRepository.findPeriodByProject(projectId).get(0);
			expensesPeriod = expenseRepository.findPeriodByProject(projectId).get(0);
		}
		
		return new java.sql.Date[]{
				getMinDate((java.sql.Date)servicesPeriod[0], (java.sql.Date)expensesPeriod[0]),
				getMaxDate((java.sql.Date) servicesPeriod[1], (java.sql.Date)expensesPeriod[1])};
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
