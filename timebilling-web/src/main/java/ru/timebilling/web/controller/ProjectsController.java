package ru.timebilling.web.controller;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.domain.Service;
import ru.timebilling.persistance.repository.ProjectRepository;
import ru.timebilling.persistance.repository.ServiceRepository;
import ru.timebilling.web.component.UserSessionComponent;

@Controller
public class ProjectsController extends AbstractController {

	@Autowired
	ProjectRepository projectsRepository;

	@Autowired
	ServiceRepository servicesRepository;
	
	@Autowired
	UserSessionComponent userInSession;
	
	@ModelAttribute("service")
	public Service getService()
	{
	    Service service = new Service();
	    return service;
	}

	@RequestMapping("/projects")
	public String projects(Model model) {
		Iterable<Project> projects = projectsRepository.findAll();
		model.addAttribute("projects", projects);
		return "projects";
	}

	@RequestMapping(value = "/projects/data/{projectId}", method = RequestMethod.GET)
	public String projectAddData(Model model, @PathVariable("projectId") Long id) {
		Project project = projectsRepository.findOne(id);
		if (project != null) {
			model.addAttribute("project", project);
			model.addAttribute("services",
					servicesRepository.findByProject(project));
		}
		return "adding-data";
	}
	
	
	@RequestMapping(value = "/projects/data/{projectId}", method = RequestMethod.POST)
	public String addDataToProject(Model model, @Valid Service service, BindingResult result,
			SessionStatus status, @PathVariable("projectId") Long id) {		
		Project project = projectsRepository.findOne(id);
		service.setProject(project);
		service.setEmployee(userInSession.getCurrentUser());
		service.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		servicesRepository.save(service);
		
		if (project != null) {
			model.addAttribute("project", projectsRepository.findOne(id));
			model.addAttribute("services",
					servicesRepository.findByProject(project));
		}
		return "adding-data";
	}
	

	@RequestMapping("/projects-table")
	public String projectsTable(Model model) {

//		Iterable<Project> projects = projectsRepository.findAll();
//		model.addAttribute("projects", projects);
		return "index";
	}

	// @RequestMapping(value="/app/{appId}/projects/{projectId}", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
	public String projectDetails(Model model, @PathVariable("projectId") Long id) {
		model.addAttribute("project", projectsRepository.findOne(id));
		return "projectDetails";
	}

	// @RequestMapping(value = "/app/{appId}/projects/new", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/projects/new", method = RequestMethod.GET)
	public String initCreationForm(Model model) {
		Project project = new Project();
		model.addAttribute("project", project);
		return "projectDetails";
	}

	// @RequestMapping(value = "/app/{appId}/projects/new", method =
	// RequestMethod.POST)
	@RequestMapping(value = "/projects/new", method = RequestMethod.POST)
	public String createProject(@Valid Project project, BindingResult result,
			SessionStatus status) {
		return createOrUpdateProject(project, result, status);
	}

	// @RequestMapping(value = "/app/{appId}/projects/{projectId}", method =
	// RequestMethod.PUT)
	@RequestMapping(value = "/projects/{projectId}", method = RequestMethod.PUT)
	public String updateProject(
			@Valid @ModelAttribute("project") Project project,
			@PathVariable("projectId") Long id, BindingResult result,
			SessionStatus status) {

		// TODO: refactor!
		Project updateableProject = projectsRepository.findOne(id);
		updateableProject.setName(project.getName());
		updateableProject.setDescription(project.getDescription());

		return createOrUpdateProject(updateableProject, result, status);

	}

	// @RequestMapping(value="/app/{appId}/deleteProject/{projectId}", method =
	// RequestMethod.GET)
	@RequestMapping(value = "/deleteProject/{projectId}", method = RequestMethod.GET)
	public String deleteProject(Model model, @PathVariable("projectId") Long id) {
		projectsRepository.delete(id);
		return buildRedirectUrl("/");
	}

	private String createOrUpdateProject(Project project, BindingResult result,
			SessionStatus status) {
		if (result.hasErrors()) {
			return "projectDetails";
		} else {
			projectsRepository.save(project);
			status.setComplete();
			return buildRedirectUrl("/");
		}
	}

}
