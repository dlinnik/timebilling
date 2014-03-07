package ru.timebilling.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.timebilling.core.domain.ProjectDetails;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;

@Controller
public class ProjectsController {

	@Autowired
	ProjectRepository projectsRepository;

    @RequestMapping("/")
    public String projects(Model model) {
    	
    	Iterable<Project> projects = projectsRepository.findAll();
    	List<ProjectDetails> pds = new ArrayList<ProjectDetails>();
    	for(Project p : projects){
    		pds.add(p.toProjectDetails());
    	}
    	
    	
        model.addAttribute("projects", pds);
        return "index";
    }

}
