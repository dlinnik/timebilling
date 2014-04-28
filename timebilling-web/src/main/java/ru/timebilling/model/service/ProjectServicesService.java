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
import ru.timebilling.rest.domain.ServiceDetails;

@Service
public class ProjectServicesService {

	@Autowired
	ServiceRepository servicesRepository;

	@Autowired
	ProjectRepository projectsRepository;

	@Autowired
	ServiceConverter converter;

	public Page<ServiceDetails> getServices(Long projectId, Pageable pageable) {
		Project project = projectsRepository.findOne(projectId);
		if (project != null) {

			Page<ru.timebilling.model.domain.Service> page = servicesRepository
					.findByProject(project, pageable);

			return PageConverter
					.convert(page)
					.using(new Function<ru.timebilling.model.domain.Service, ServiceDetails>() {
						@Override
						public ServiceDetails apply(
								ru.timebilling.model.domain.Service service) {
							return converter.toServiceDetails(service);
						}
					});
		}
		return null;
	}

	public ServiceDetails save(ServiceDetails details) throws ParseException {
		ru.timebilling.model.domain.Service service = converter
				.fromServiceDetails(details);
		service = servicesRepository.save(service);
		return converter.toServiceDetails(service);
	}

}
