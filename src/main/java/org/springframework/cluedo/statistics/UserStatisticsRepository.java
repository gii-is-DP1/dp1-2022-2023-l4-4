package org.springframework.cluedo.statistics;

import java.util.List;

import org.springframework.cluedo.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends CrudRepository<UserStatistics,Integer>{

    List<UserStatistics> findAll();
    
    @Query("SELECT u FROM UserStatistics u WHERE u.user = :user")
    UserStatistics findMyStatistics(@Param("user") User user);

  
    @Modifying
    @Query("UPDATE UserStatistics u SET u.user=null WHERE u.user.id = :id")
    void setNullUserStatistic(@Param("id")Integer id);
}
