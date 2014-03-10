package ru.timebilling.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.persistance.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByEmail(String email);

}
