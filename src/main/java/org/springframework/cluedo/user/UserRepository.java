package org.springframework.cluedo.user;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User, Integer>{
	
List<User> findAll();

User findByTag(String tag);

@Query("SELECT u.friends FROM User u where u.id=:id ")
List<User> findFriendsById(@Param("id")Integer id);

@Query("SELECT u FROM User u WHERE u.username=:username")
Optional<User> findByUsername(@Param("username") String username);

@Query("SELECT u.tag FROM User u")
List<String> findAllTags();
}


