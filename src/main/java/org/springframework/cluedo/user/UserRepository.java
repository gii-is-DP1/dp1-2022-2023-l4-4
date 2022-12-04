package org.springframework.cluedo.user;


import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User, Integer>{
	
List<User> findAll();

Optional<User> findById(Integer id);

void deleteById(Integer id);

@Query("SELECT u FROM User u WHERE u.username=:username")
Optional<User> findByUsername(@Param("username") String username);

@Query("SELECT u.achievements FROM User u WHERE u.id = :userId")
List<Achievement> getAllMyAchievements(@Param("userId") Integer userId);
}


