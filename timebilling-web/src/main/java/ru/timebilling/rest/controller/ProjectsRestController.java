package ru.timebilling.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.core.domain.ProjectDetails;
import ru.timebilling.core.domain.ServiceDetails;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;

@Controller
public class ProjectsRestController extends BaseAPIController{
	

	@Autowired
	ProjectRepository projectsRepository;
	  
    @RequestMapping("/projects")
    @ResponseBody
    public Iterable<Project> projects() {
    	
    	return projectsRepository.findAll();
    	
//    	Iterable<Project> projects = projectsRepository.findAll();
//    	List<ProjectDetails> pds = new ArrayList<ProjectDetails>();
//    	for(Project p : projects){
//    		pds.add(p.toProjectDetails());
//    	}    	
//    	return pds;
    }
    
	@RequestMapping(value="/projects/search", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<Project> filterProjectByNameOrCustomer(
			Model model, @RequestParam("name") String name)
	{
		return projectsRepository.findByNameStartingWithOrClientStartingWith(name, name);
	}

    
    
}
