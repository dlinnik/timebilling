package ru.timebilling.model.service.conversion;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ru.timebilling.model.domain.BaseRecordEntity;
import ru.timebilling.model.repository.BillingReportRepository;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.rest.domain.Record;
import ru.timebilling.web.component.UserSessionComponent;

public abstract class AbstractRecordConverter <T extends BaseRecordEntity>{
	
	@Autowired
	UserSessionComponent userInSession;
	
	@Autowired
	ProjectRepository projectsRepository;
	
	@Autowired
	UserRepository usersRepository;
	
	@Autowired
	BillingReportRepository billingReportRepository;

	
	public Record toRecord(T t){
		Record r = toRecordInternal(t);
		setAdditionalFields(r, t);
		return r;
	}
	
	public T fromRecord(T t, Record r) throws ParseException{
		t = fromRecordInternal(t, r);
		setAdditionalFields(t, r);
		return t;
	}

	
	
	protected abstract void setAdditionalFields(Record record, T t);
	protected abstract void setAdditionalFields(T t, Record record);
	

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
		details.setDisable(t.getReport()!=null);
		details.setExcludedFromReport(t.isExcluded());
		details.setReport(t.getReport()!=null ? t.getReport().getId() : null);
		return details;
	}
	
	protected T fromRecordInternal(T t, Record r) throws ParseException{
		//TODO: date!
//		t.setDate(new java.sql.Date(dateFormat.parse(sd.getDay() + "/" + sd.getMonth() + "/" + sd.getYear()).getTime()));
		
		Date d1 = r.getDate();
		Date d2 = Calendar.getInstance().getTime();
		
		t.setDate(ConversionUtils.convertToSQLDate(r.getDate()));
		t.setComment(r.getComment());
		if(r.getUser()!=null){
			t.setEmployee(usersRepository.findOne(r.getUser()));
		}else{
			t.setEmployee(userInSession.getCurrentUser());
		}
		if(r.getReport()!=null){
			t.setReport(billingReportRepository.findOne(r.getReport()));
		}
		t.setProject(projectsRepository.findOne(r.getProject()));
		
		return t;
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
