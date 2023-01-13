package org.springframework.cluedo.achievement;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    List<Achievement> findAll();

    Optional<Achievement> findById(Integer id);

    @Query("SELECT u.achievements FROM User u WHERE u.id = :userId")
    List<Achievement> findAllMyAchievements(@Param("userId") Integer userId);

    @Modifying
    @Query(value="DELETE FROM users_achievements WHERE user_id = ?1",nativeQuery = true)
    void deleteUsersAchievementsUserToDelete(@Param("id") Integer id);
}
