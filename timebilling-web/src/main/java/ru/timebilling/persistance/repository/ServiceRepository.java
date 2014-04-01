package ru.timebilling.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.domain.Service;
import ru.timebilling.persistance.domain.User;

public interface ServiceRepository extends CrudRepository<Service, Long>{

	public Iterable<Service> findByEmployee(User user);
	public Iterable<Service> findByProject(Project project);

}
