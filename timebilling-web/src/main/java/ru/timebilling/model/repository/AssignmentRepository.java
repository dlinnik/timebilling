package ru.timebilling.model.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.model.domain.Assignment;

public interface AssignmentRepository extends CrudRepository<Assignment, Long>{

}
