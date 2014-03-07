package ru.timebilling;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.repository.ProjectRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {

		ProjectRepository repo = event.getApplicationContext().getBean(ProjectRepository.class);
		repo.save(new Project("pr1", "project 1"));
		repo.save(new Project("pr2", "project 2"));
		repo.save(new Project("pr3", "project 3"));
		repo.save(new Project("pr4", "project 4"));
		repo.save(new Project("pr5", "project 5"));
	}
}
