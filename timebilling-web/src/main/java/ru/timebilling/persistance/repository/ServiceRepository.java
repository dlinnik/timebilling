package ru.timebilling.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ru.timebilling.persistance.domain.Project;
import ru.timebilling.persistance.domain.Service;
import ru.timebilling.persistance.domain.User;

public interface ServiceRepository extends PagingAndSortingRepository<Service, Long>{

	public Iterable<Service> findByEmployee(User user);
	public Iterable<Service> findByProject(Project project);
	public Page<Service> findByProject(Project project, Pageable pageable);
	public Iterable<Service> findByProjectOrderByDateDesc(Project project);
}
