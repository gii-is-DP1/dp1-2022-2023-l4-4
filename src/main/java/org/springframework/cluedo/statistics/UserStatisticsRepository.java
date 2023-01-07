package org.springframework.cluedo.statistics;

import java.util.List;

import org.springframework.cluedo.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends CrudRepository<UserStatistics,Integer>{

    List<UserStatistics> findAll();
    
    @Query("SELECT u FROM UserStatistics u WHERE u.user = :user")
    UserStatistics findMyStatistics(@Param("user") User user);
}
