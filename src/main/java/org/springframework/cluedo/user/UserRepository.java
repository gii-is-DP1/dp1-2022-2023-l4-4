package org.springframework.cluedo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends  CrudRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE user.id LIKE id")
    User findUserById(@Param("id")int id);
	
}
