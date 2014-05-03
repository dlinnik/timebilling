package ru.timebilling.model.service.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.repository.ServiceRepository;
import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.rest.domain.Record;
import ru.timebilling.web.component.UserSessionComponent;

@Service
public class ServiceConverter extends AbstractRecordConverter<ru.timebilling.model.domain.Service>{
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	ProjectRepository projectsRepository;
	
	@Autowired
	ServiceRepository servicesRepository;
	
	@Autowired
	UserSessionComponent userInSession;

	
	@Override
	public void setAdditionalFields(Record record,
			ru.timebilling.model.domain.Service t) {
		record.setValue(t.getSpentTime());
	}
	
//	public ru.timebilling.model.domain.Service fromServiceDetails(Record sd) throws ParseException{
//        final SimpleDateFormat dateFormat = createDateFormat();
//        final DecimalFormat dFormat = createDecimalFormat();
//        
//		ru.timebilling.model.domain.Service service = null;
//		
//		if(sd.getId()!=null){
//			//update
//			service = servicesRepository.findOne(sd.getId());
//			service.setDate(new java.sql.Date(dateFormat.parse(sd.getDay() + "/" + sd.getMonth() + "/" + sd.getYear()).getTime()));
//			service.setComment(sd.getComment());
//			service.setSpentTime(new BigDecimal(dFormat.parse(sd.getSpentTime()).doubleValue()));
//		}else{
//			//create
//			service = new ru.timebilling.model.domain.Service();
//			service.setId(sd.getId());
//			service.setDate(new java.sql.Date(dateFormat.parse(sd.getDay() + "/" + sd.getMonth() + "/" + sd.getYear()).getTime()));
//			service.setComment(sd.getComment());
//			service.setSpentTime(new BigDecimal(dFormat.parse(sd.getSpentTime()).doubleValue()));
//			
//			service.setEmployee(userRepository.findOne(sd.getUser()));
//			service.setProject(projectsRepository.findOne(sd.getProject()));
//		}
//		return service;
//	}
	
    

}
