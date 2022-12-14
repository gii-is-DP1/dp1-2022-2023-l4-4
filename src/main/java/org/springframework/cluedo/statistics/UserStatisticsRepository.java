package org.springframework.cluedo.statistics;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends CrudRepository<UserStatistics,Integer>{

    List<UserStatistics> findAll();
}
