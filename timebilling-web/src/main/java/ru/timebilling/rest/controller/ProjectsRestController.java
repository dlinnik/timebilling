package ru.timebilling.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.core.domain.ProjectDetails;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;

@Controller
public class ProjectsRestController {
	

	@Autowired
	ProjectRepository projectsRepository;
	  
    @RequestMapping("/projectsREST")
    @ResponseBody
    public List<ProjectDetails> projects() {
    	
    	Iterable<Project> projects = projectsRepository.findAll();
    	List<ProjectDetails> pds = new ArrayList<ProjectDetails>();
    	for(Project p : projects){
    		pds.add(p.toProjectDetails());
    	}    	
    	return pds;
    }
}
