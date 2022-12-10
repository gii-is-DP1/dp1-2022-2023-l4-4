package org.springframework.cluedo.statistics;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends CrudRepository<UserStatistics,Integer>{

}
