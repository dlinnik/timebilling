package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.domain.Project;
import ru.timebilling.model.repository.ProjectRepository;
import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.model.service.conversion.ProjectConverter;
import ru.timebilling.rest.domain.ProjectDetails;

@Controller
public class ProjectsRestController extends BaseAPIController{
	

	@Autowired
	ProjectRepository projectsRepository;
	
	@Autowired
	ProjectConverter projectConverter;
	
	  
    @RequestMapping("/projects")
    @ResponseBody
    public Iterable<Project> projects() {
    	
    	if(userInSession.isAdmin()){
    		return projectsRepository.findAll();
    	}
    	return projectsRepository.findAssigned(userInSession.getCurrentUser().getId());
    }
    
	@RequestMapping(value = "/project/{projectId}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Project project(Model model, @PathVariable("projectId") Long id) {
		return projectsRepository.findOne(id);
	}

    
	@RequestMapping(value="/projects/search", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<Project> filterProjectByNameOrCustomer(
			Model model, @RequestParam("name") String name)
	{
		return projectsRepository.findByNameStartingWithOrClientStartingWith(name, name);
	}
	
	@RequestMapping(value="/admin/project", method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public ProjectDetails createProject(@RequestBody ProjectDetails project) throws ApplicationException {    
		return 
				projectConverter.toDTO(
						projectsRepository.save(projectConverter.toEntity(project)));
	}
	
	@RequestMapping(value="/admin/project", method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	@Deprecated
	public ProjectDetails updateProject(@RequestBody ProjectDetails project) throws ApplicationException {   
		return 
				projectConverter.toDTO(
						projectsRepository.save(projectConverter.toEntity(project)));
	}
	
	@RequestMapping(value="/admin/project/{projectId}", method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-type=application/json"})
	@ResponseBody
	public ProjectDetails updateProject(@PathVariable("projectId") Long id, @RequestBody ProjectDetails project) throws ApplicationException {
		
		project.setId(id);
		
		return 
				projectConverter.toDTO(
						projectsRepository.save(projectConverter.toEntity(project)));
	}

	
	
	@RequestMapping(value = "/admin/project/{projectId}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ProjectDetails adminProject(Model model, @PathVariable("projectId") Long id) {
		return projectConverter.toDTO(projectsRepository.findOne(id));
	}

	
	
}
