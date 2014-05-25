package ru.timebilling.model.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.ExpenseRepository;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.service.conversion.ExpenseConverter;
import ru.timebilling.model.service.conversion.PageConverter;
import ru.timebilling.rest.domain.Record;

import com.google.common.base.Function;

@Service
public class ProjectExpensesService extends BaseRecordService<Expense>{

	@Autowired
	ExpenseRepository expenseRepository;
	
	@Autowired
	ProjectRepository projectsRepository;

	@Autowired
	ExpenseConverter converter;

	public Page<Record> getExpenses(Long projectId, Pageable pageable) {
		Project project = projectsRepository.findOne(projectId);
		if (project != null) {

			Page<Expense> page = expenseRepository.findByProject(project, pageable);

			return PageConverter
					.convert(page)
					.using(new Function<Expense, Record>() {
						@Override
						public Record apply(
								Expense service) {
							Record r = converter.toRecord(service);
							return r;
						}
					});
		}
		return null;
	}

	public Record save(Record details) throws ParseException, ApplicationException {
		Expense e = new Expense();
		if(!details.isNew()){
			e = expenseRepository.findOne(details.getId());
			canUpdateRecord(e, details);

		}
		e = converter.fromRecord(e, details);
		e = expenseRepository.save(e);
		return converter.toRecord(e);
	}

	public void delete(Long id){
		expenseRepository.delete(id);
	}


}
