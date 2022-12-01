package org.springframework.cluedo.achievement;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    List<Achievement> findAll();
    
    @Query("SELECT a FROM Achievement a JOIN User u where u.id = ")
    List<Achievement> findAllMyAchievements(Integer id);
    
}
