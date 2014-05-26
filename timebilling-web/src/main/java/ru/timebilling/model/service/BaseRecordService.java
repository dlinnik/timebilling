package ru.timebilling.model.service;

//import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.BaseRecordEntity;
import ru.timebilling.model.domain.BillingReport;

public abstract class BaseRecordService <E extends BaseRecordEntity>{
	
	public void validate(E e) throws ApplicationException{
		if(e.getReport()!=null){
			BillingReport report = e.getReport();
			if(e.getDate()!=null && 
					e.getDate().compareTo(report.getStartDate())>=0 && 
					e.getDate().compareTo(report.getEndDate())<=0){
				// it's ok
				return;
			}
			throw new ApplicationException("Невозможно сохранить запись так как дата находится вне периода отчета");
		}
	}

}
