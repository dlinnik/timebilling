package ru.timebilling.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.persistance.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>{

}
