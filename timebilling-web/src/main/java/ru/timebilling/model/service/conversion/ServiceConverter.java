package ru.timebilling.model.service.conversion;

import org.springframework.stereotype.Service;

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
	}

}
