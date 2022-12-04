package org.springframework.cluedo.achievement;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cluedo.enumerates.Metric;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    List<Achievement> findAll();

    Optional<Achievement> findById(Integer id);
}
