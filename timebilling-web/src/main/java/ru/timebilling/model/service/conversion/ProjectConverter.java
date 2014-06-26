package ru.timebilling.model.service.conversion;

import java.util.HashSet;
import java.util.Iterator;
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
import ru.timebilling.model.repository.AssignmentRepository;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.model.service.UserDetailsServiceImpl;
import ru.timebilling.rest.domain.ProjectDetails;
import ru.timebilling.rest.domain.UserInProject;

@Service
public class ProjectConverter extends
		AbstractConverter<Project, ProjectDetails> {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	AssignmentRepository assignmentRepository;

	static final Logger logger = LoggerFactory
			.getLogger(ProjectConverter.class);

	@Override
	public Project toEntity(ProjectDetails v) throws ApplicationException {
		Project p = new Project();
		if (v.getId() != null) {
			p = projectRepository.findOne(v.getId());
		}
		if (p == null) {
			throw new ApplicationException("Проект с идентификатором = "
					+ v.getId() + " не существует.");
		}
		p.setId(v.getId());
		p.setName(v.getName());
		p.setDescription(v.getDescription());
		p.setClient(v.getClient());

		Set<Assignment> assignments = p.getAssignments() == null ? 
				new HashSet<Assignment>() : p.getAssignments();

		//очищаем предыдущие назначения
		for(Assignment a : assignments){
			a.setProject(null);
		}
		assignments.clear();
		p.setAssignments(assignments);

		if (v.getAssignments() != null) {
			for (UserInProject u : v.getAssignments()) {
				Assignment a = null;
				if(u.getId()!=null){
					a = assignmentRepository.findOne(u.getId());
				}else{
					a = new Assignment();
				}
				if(a == null){
					throw new ApplicationException(
							"Назначение с идентификатором = "
									+ u.getId() + " не существует.");
				}
				
				assignments.add(a);
				
				User user = null;
				if (u.getUserId() != null) {
					user = userRepository.findOne(u.getUserId());
					if (user == null) {
						throw new ApplicationException(
								"Пользователь с идентификатором = "
										+ u.getUserId() + " не существует.");
					}
				} else {
					user = userDetailsService.createNewUser(u.getUserName(),
							u.getUserEmail());
				}
				a.setUser(user);
				a.setProject(p);
				a.setRate(u.getRate());
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
		for (Assignment a : e.getAssignments()) {
			UserInProject uip = new UserInProject();
			uip.setId(a.getId());
			uip.setUserId(a.getUser().getId());
			uip.setUserName(a.getUser().getUsername());
			uip.setUserEmail(a.getUser().getEmail());
			uip.setRate(a.getRate());
			uips.add(uip);
		}

		return p;
	}

}
