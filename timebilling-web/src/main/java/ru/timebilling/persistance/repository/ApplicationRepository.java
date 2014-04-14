package ru.timebilling.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.timebilling.persistance.domain.Application;

@RepositoryRestResource(collectionResourceRel = "app", path = "app")
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long>{
	
	public Application findByName(@Param("name") String name);


}
