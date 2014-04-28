package ru.timebilling.model.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.model.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByEmail(String email);

}
