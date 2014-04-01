package ru.timebilling.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.core.domain.ServiceDetails;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;
import ru.timebilling.persistance.repository.ServiceRepository;
import ru.timebilling.service.conversion.ServiceConverter;

@Service
public class ProjectServicesService {
	
	@Autowired
	ServiceRepository servicesRepository;
	
	@Autowired
	ProjectRepository projectsRepository;
	
	
	
	@Autowired
	ServiceConverter converter;

	public Iterable<ru.timebilling.persistance.domain.Service> getServices(Long projectId){
		Project project = projectsRepository.findOne(projectId);
		if (project != null) {
			return servicesRepository.findByProjectOrderByDateDesc(project);
		}
		return null;
	}
	
	public ServiceDetails save(ServiceDetails details) throws ParseException{
		ru.timebilling.persistance.domain.Service service = converter.fromServiceDetails(details);
		service = servicesRepository.save(service);
		return converter.toServiceDetails(service);
	}
	

}
