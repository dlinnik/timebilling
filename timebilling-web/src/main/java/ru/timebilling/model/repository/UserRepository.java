package ru.timebilling.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.timebilling.model.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByEmail(String email);

    @Query("SELECT e FROM user e WHERE UPPER(e.username) LIKE UPPER(:username)")
	public Iterable<User> filterByName(@Param("username") String username);
	
    @Query("SELECT e FROM user e WHERE UPPER(e.email) LIKE UPPER(:email)")
	public Iterable<User> filterByEmail(@Param("email") String email);
	

}
