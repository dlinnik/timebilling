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
import ru.timebilling.model.domain.Role;
import ru.timebilling.model.domain.User;
import ru.timebilling.model.domain.UserRoleEnum;
import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.model.service.UserDetailsServiceImpl;
import ru.timebilling.rest.domain.ProjectDetails;
import ru.timebilling.rest.domain.UserInProject;

@Service
public class ProjectConverter extends AbstractConverter<Project, ProjectDetails>{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	
	static final Logger logger = LoggerFactory.getLogger(ProjectConverter.class);

	
	@Override
	public Project toEntity(ProjectDetails v) throws ApplicationException{
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
				User user = null;
				if(u.getUserId()!=null){
					user = userRepository.findOne(u.getUserId());
					if(user == null){
						throw new ApplicationException("Пользователь с идентификатором = " + 
					u.getUserId() + " не существует.");
					}
				}else{
					user = userDetailsService.createNewUser(u.getUserName(), u.getUserEmail());
				}					
				a.setUser(user);
				a.setProject(p);
				a.setRate(u.getRate());
				assignments.add(a);
			}
		}
		
		logger.info("returns project with assignment " + p.getAssignments());
		
		return p;
	}

	@Override
	public ProjectDetails toDTO(Project e) {
		ProjectDetails p = new ProjectDetails();
		p.setId(e.getId());
		p.setName(e.getName());
		p.setDescription(e.getDescription());
		p.setClient(e.getClient());
		Set<UserInProject> uips = new HashSet<UserInProject>();
		p.setAssignments(uips);
		for(Assignment a : e.getAssignments()){
			UserInProject uip = new UserInProject();
			uip.setUserId(a.getUser().getId());
			uip.setUserName(a.getUser().getUsername());
			uip.setUserEmail(a.getUser().getEmail());
			uip.setRate(a.getRate());
			uips.add(uip);
		}
		
		return p;
	}

}
