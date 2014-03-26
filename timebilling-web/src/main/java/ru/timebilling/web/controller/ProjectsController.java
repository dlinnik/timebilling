package ru.timebilling.web.controller;


import java.util.Arrays;

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
import ru.timebilling.persistance.repository.ProjectRepository;

@Controller
public class ProjectsController extends AbstractController{

	@Autowired
	ProjectRepository projectsRepository;

//    @RequestMapping("/app/{appId}/")
	@RequestMapping("/projects")
    public String projects(Model model) {
    	
    	Iterable<Project> projects = projectsRepository.findAll();    	
        model.addAttribute("projects", projects);
        return "projects";
    }

	@RequestMapping("/projects-table")
    public String projectsTable(Model model) {
    	
    	Iterable<Project> projects = projectsRepository.findAll();    	
        model.addAttribute("projects", projects);
        return "index";
    }
	
//    @RequestMapping(value="/app/{appId}/projects/{projectId}", method = RequestMethod.GET)
    @RequestMapping(value="/projects/{projectId}", method = RequestMethod.GET)    
	public String projectDetails(Model model, 
    		@PathVariable("projectId") Long id){
    	model.addAttribute("project", projectsRepository.findOne(id));
    	return "projectDetails";
    }
    
//    @RequestMapping(value = "/app/{appId}/projects/new", method = RequestMethod.GET)
    @RequestMapping(value = "/projects/new", method = RequestMethod.GET)    
    public String initCreationForm(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "projectDetails";
    }
    
//    @RequestMapping(value = "/app/{appId}/projects/new", method = RequestMethod.POST)
    @RequestMapping(value = "/projects/new", method = RequestMethod.POST)    
    public String createProject(@Valid Project project, BindingResult result, SessionStatus status) {
        return createOrUpdateProject(project, result, status);
    }
    
//    @RequestMapping(value = "/app/{appId}/projects/{projectId}", method = RequestMethod.PUT)
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.PUT)    
    public String updateProject(
    		@Valid
    		@ModelAttribute("project")
    		Project project, @PathVariable("projectId") Long id, 
    		BindingResult result, SessionStatus status) {
    	
    	//TODO: refactor!
    	Project updateableProject = projectsRepository.findOne(id);
    	updateableProject.setName(project.getName());
    	updateableProject.setDescription(project.getDescription());
    	
        return createOrUpdateProject(updateableProject, result, status);
    	
    }

//    @RequestMapping(value="/app/{appId}/deleteProject/{projectId}", method = RequestMethod.GET)
    @RequestMapping(value="/deleteProject/{projectId}", method = RequestMethod.GET)    
    public String deleteProject(Model model, 
        		@PathVariable("projectId") Long id){
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
