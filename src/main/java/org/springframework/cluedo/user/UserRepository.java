package org.springframework.cluedo.user;


import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends  CrudRepository<User, Integer>{
	
List<User> findAll();

@Query("SELECT u FROM User u WHERE u.username=:username")
Optional<User> findByUsername(@Param("username") String username);
}
