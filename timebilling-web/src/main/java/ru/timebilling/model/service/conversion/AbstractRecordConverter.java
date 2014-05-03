package ru.timebilling.model.service.conversion;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import ru.timebilling.model.domain.BaseRecordEntity;
import ru.timebilling.rest.domain.Record;
import ru.timebilling.web.component.UserSessionComponent;

public abstract class AbstractRecordConverter <T extends BaseRecordEntity>{
	
	@Autowired
	UserSessionComponent userInSession;
	
	public Record toRecord(T t){
		Record r = toRecordInternal(t);
		setAdditionalFields(r, t);
		return r;
	}
	
	protected abstract void setAdditionalFields(Record record, T t);



	protected Record toRecordInternal(T t){
        final SimpleDateFormat dateFormat = createDateFormat();
        final DecimalFormat dFormat = createDecimalFormat();

		Record details = new Record();
		details.setId(t.getId());
//		details.setDate(dateFormat.format(service.getDate()));
		details.setDate(t.getDate());
		details.setComment(t.getComment());
//		details.setValue(service.getSpentTime());
		
		details.setUser(t.getEmployee().getId());
		details.setUserScreenName(t.getEmployee().getUsername());		
		details.setProject(t.getProject().getId());
		details.setAuth(details.getUser().equals(userInSession.getCurrentUser().getId()));
		return details;
	}
	
		
    protected SimpleDateFormat createDateFormat() {
    	final String format = "dd/MM/yyyy";
    	final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	dateFormat.setLenient(false);
    	return dateFormat;
  }

    protected DecimalFormat createDecimalFormat() {
    	final String format = "#,##";
    	final DecimalFormat dFormat = new DecimalFormat(format);
    	return dFormat;
  }


}
