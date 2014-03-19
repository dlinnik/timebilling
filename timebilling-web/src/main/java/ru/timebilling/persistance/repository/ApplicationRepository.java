package ru.timebilling.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.persistance.domain.Application;


public interface ApplicationRepository extends CrudRepository<Application, Long>{
	
	public Application findByName(String name);


}
