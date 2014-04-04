package ru.timebilling.service.conversion;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.core.domain.ServiceDetails;
import ru.timebilling.persistance.repository.ProjectRepository;
import ru.timebilling.persistance.repository.ServiceRepository;
import ru.timebilling.persistance.repository.UserRepository;
import ru.timebilling.web.component.UserSessionComponent;

@Service
public class ServiceConverter {
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	ProjectRepository projectsRepository;
	
	@Autowired
	ServiceRepository servicesRepository;
	
	@Autowired
	UserSessionComponent userInSession;
	
	public ServiceDetails toServiceDetails(ru.timebilling.persistance.domain.Service service){
        final SimpleDateFormat dateFormat = createDateFormat();
        final DecimalFormat dFormat = createDecimalFormat();

		ServiceDetails details = new ServiceDetails();
		details.setId(service.getId());
		details.setDate(dateFormat.format(service.getDate()));
		details.setComment(service.getComment());
		details.setSpentTime(dFormat.format(service.getSpentTime()));
		
		details.setUser(service.getEmployee().getId());
		details.setUserScreenName(service.getEmployee().getUsername());		
		details.setProject(service.getProject().getId());
		details.setEditable(details.getUser().equals(userInSession.getCurrentUser().getId()));
		return details;
	}
	
	public ru.timebilling.persistance.domain.Service fromServiceDetails(ServiceDetails sd) throws ParseException{
        final SimpleDateFormat dateFormat = createDateFormat();
        final DecimalFormat dFormat = createDecimalFormat();
        
		ru.timebilling.persistance.domain.Service service = null;
		
		if(sd.getId()!=null){
			//update
			service = servicesRepository.findOne(sd.getId());
			service.setDate(new java.sql.Date(dateFormat.parse(sd.getDay() + "/" + sd.getMonth() + "/" + sd.getYear()).getTime()));
			service.setComment(sd.getComment());
			service.setSpentTime(new BigDecimal(dFormat.parse(sd.getSpentTime()).doubleValue()));
		}else{
			//create
			service = new ru.timebilling.persistance.domain.Service();
			service.setId(sd.getId());
			service.setDate(new java.sql.Date(dateFormat.parse(sd.getDay() + "/" + sd.getMonth() + "/" + sd.getYear()).getTime()));
			service.setComment(sd.getComment());
			service.setSpentTime(new BigDecimal(dFormat.parse(sd.getSpentTime()).doubleValue()));
			
			service.setEmployee(userRepository.findOne(sd.getUser()));
			service.setProject(projectsRepository.findOne(sd.getProject()));
		}
		return service;
	}
	
    private SimpleDateFormat createDateFormat() {
    	final String format = "dd/MM/yyyy";
    	final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	dateFormat.setLenient(false);
    	return dateFormat;
  }

    private DecimalFormat createDecimalFormat() {
    	final String format = "#,##";
    	final DecimalFormat dFormat = new DecimalFormat(format);
    	return dFormat;
  }
    

}
