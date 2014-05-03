package ru.timebilling.model.service.conversion;

import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.Expense;
import ru.timebilling.rest.domain.Record;

@Service
public class ExpenseConverter extends AbstractRecordConverter<Expense>{

	@Override
	public void setAdditionalFields(Record record, Expense t) {
		record.setValue(t.getSpentMoney());
	}

}
