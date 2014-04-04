package ru.timebilling.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;

import ru.timebilling.core.domain.ServiceDetails;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;
import ru.timebilling.persistance.repository.ServiceRepository;
import ru.timebilling.service.conversion.PageConverter;
import ru.timebilling.service.conversion.ServiceConverter;

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

			Page<ru.timebilling.persistance.domain.Service> page = servicesRepository
					.findByProject(project, pageable);

			return PageConverter
					.convert(page)
					.using(new Function<ru.timebilling.persistance.domain.Service, ServiceDetails>() {
						@Override
						public ServiceDetails apply(
								ru.timebilling.persistance.domain.Service service) {
							return converter.toServiceDetails(service);
						}
					});
		}
		return null;
	}

	public ServiceDetails save(ServiceDetails details) throws ParseException {
		ru.timebilling.persistance.domain.Service service = converter
				.fromServiceDetails(details);
		service = servicesRepository.save(service);
		return converter.toServiceDetails(service);
	}

}
