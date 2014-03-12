package ru.timebilling;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.domain.Role;
import ru.timebilling.persistance.domain.User;
import ru.timebilling.persistance.repository.ProjectRepository;
import ru.timebilling.persistance.repository.UserRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {

		//TODO: remove me
		//persist initial data
		ProjectRepository repo = event.getApplicationContext().getBean(ProjectRepository.class);
		repo.save(new Project(null, "pr1", "project 1"));
		repo.save(new Project(null, "pr2", "project 2"));
		repo.save(new Project(null, "pr3", "project 3"));
		repo.save(new Project(null, "pr4", "project 4"));
		repo.save(new Project(null, "pr5", "project 5"));
		
		UserRepository usersRepo = event.getApplicationContext().getBean(UserRepository.class);
		
		User u = new User();
		u.setUsername("admin");
		u.setEmail("admin@timebilling.ru");
		u.setPassword("passw0rd");
		Role r = new Role();
		r.setRole(1);
		u.setRole(r);
		usersRepo.save(u);

		u = new User();
		u.setUsername("user");
		u.setEmail("user@timebilling.ru");
		u.setPassword("passw0rd");
		r = new Role();
		r.setRole(2);
		u.setRole(r);
		
		usersRepo.save(u);
				
	}
}
