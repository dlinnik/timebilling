package ru.timebilling.model.service;

//import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.BaseRecordEntity;
import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.rest.domain.Record;

public abstract class BaseRecordService <E extends BaseRecordEntity>{
	
	public void canUpdateRecord(E e, Record r) throws ApplicationException{
		if(e.getReport()!=null){
			BillingReport report = e.getReport();
			if(r.getDate()!=null && 
					r.getDate().compareTo(report.getStartDate())>=0 && 
					r.getDate().compareTo(report.getEndDate())<=0){
				// it's ok
				return;
			}
			throw new ApplicationException("Невозможно обновить запись так как новая дата вне периода отчета");
		}
	}

}
