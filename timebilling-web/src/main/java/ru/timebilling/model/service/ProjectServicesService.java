package ru.timebilling.model.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;

import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.repository.ServiceRepository;
import ru.timebilling.model.service.conversion.PageConverter;
import ru.timebilling.model.service.conversion.ServiceConverter;
import ru.timebilling.rest.domain.Record;

@Service
public class ProjectServicesService extends BaseRecordService<ru.timebilling.model.domain.Service>{

	@Autowired
	ServiceRepository servicesRepository;

	@Autowired
	ProjectRepository projectsRepository;

	@Autowired
	ServiceConverter converter;

	public Page<Record> getServices(Long projectId, Pageable pageable) {
		Project project = projectsRepository.findOne(projectId);
		if (project != null) {

			Page<ru.timebilling.model.domain.Service> page = servicesRepository
					.findByProject(project, pageable);

			return PageConverter
					.convert(page)
					.using(new Function<ru.timebilling.model.domain.Service, Record>() {
						@Override
						public Record apply(
								ru.timebilling.model.domain.Service service) {
							Record r = converter.toRecord(service);
							return r;
						}
					});
		}
		return null;
	}

	public Record save(Record details) throws ParseException, ApplicationException {
		ru.timebilling.model.domain.Service service = new ru.timebilling.model.domain.Service();
		if(!details.isNew()){
			service = servicesRepository.findOne(details.getId());
			canUpdateRecord(service, details);
		}
		service = converter.fromRecord(service, details);
		service = servicesRepository.save(service);
		return converter.toRecord(service);
	}
	
	public void delete(Long id){
		servicesRepository.delete(id);
	}

}
