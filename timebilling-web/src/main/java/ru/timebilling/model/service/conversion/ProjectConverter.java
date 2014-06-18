package ru.timebilling.model.service.conversion;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.timebilling.config.DataConfig;
import ru.timebilling.model.domain.Assignment;
import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.rest.domain.ProjectDetails;
import ru.timebilling.rest.domain.UserInProject;

@Service
public class ProjectConverter extends AbstractConverter<Project, ProjectDetails>{

	@Autowired
	UserRepository userRepository;
	
	static final Logger logger = LoggerFactory.getLogger(ProjectConverter.class);

	
	@Override
	public Project toEntity(ProjectDetails v) {
		Project p = new Project();
		p.setId(v.getId());
		p.setName(v.getName());
		p.setDescription(v.getDescription());
		p.setClient(v.getClient());
		
		Set<Assignment> assignments = new HashSet<Assignment>();
		p.setAssignments(assignments);
		
		if(v.getAssignments()!=null){
			for(UserInProject u : v.getAssignments()){
				Assignment a = new Assignment();
				if(u.getUserId()!=null){ //пока поддерживается только для существующих пользователей 
					a.setUser(userRepository.findOne(u.getUserId()));
					a.setProject(p);
					a.setRate(u.getRate());
					assignments.add(a);
				}
			}
		}
		
		logger.info("returns project with assignment " + p.getAssignments());
		
		return p;
	}

	@Override
	public ProjectDetails toDTO(Project e) {
		// TODO Auto-generated method stub
		return null;
	}

}
