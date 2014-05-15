package ru.timebilling.model.service.conversion;

import java.util.Set;

import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.Assignment;
import ru.timebilling.rest.domain.Record;

@Service
public class ServiceConverter extends AbstractRecordConverter<ru.timebilling.model.domain.Service>{
	
	@Override
	public void setAdditionalFields(Record record,
			ru.timebilling.model.domain.Service t) {
		record.setValue(t.getSpentTime());
	}
	
	
	
	@Override
	protected void setAdditionalFields(ru.timebilling.model.domain.Service t,
			Record record) {
		t.setSpentTime(record.getValue());
		Set<Assignment> assignments = t.getProject().getAssignments();
		for(Assignment a : assignments){
			if(a.getUser().getId().equals(t.getEmployee().getId())){
				t.setSpentMoney(t.getSpentTime().multiply(a.getRate()));
				return;
			}
		}
		
	}

}
