package ru.timebilling.model.service;

//import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.BaseRecordEntity;
import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.repository.BillingReportRepository;

@Service
public abstract class BaseRecordService <E extends BaseRecordEntity>{

	@Autowired
	private BillingReportRepository reportsRepository;

	
	public void validate(E e) throws ApplicationException{
		if(e.getReport()!=null){
			//проверяем что запись, создаваемая в отчете, попадает в отчетный период
			BillingReport report = e.getReport();
			if(e.getDate()!=null && 
					e.getDate().compareTo(report.getStartDate())>=0 && 
					e.getDate().compareTo(report.getEndDate())<=0){
				// it's ok
				return;
			}
			throw new ApplicationException("Невозможно сохранить запись так как дата находится вне периода отчета");
		}
		
		// проверяем что на дату записи нет созданных ранее отчетов
		
		Iterable<BillingReport> reports = getReportsRepository().findByProjectAndDateInReportPeriod(
				e.getProject().getId(), e.getDate());
		
		if(reports.iterator().hasNext()){
			throw new ApplicationException("Невозможно сохранить запись так как указанная дата входит в период ранее созданного отчета");
		}
		
		
	}


	public BillingReportRepository getReportsRepository() {
		return reportsRepository;
	}
	
	

}
