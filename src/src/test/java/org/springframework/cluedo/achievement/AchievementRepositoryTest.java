package org.springframework.cluedo.achievement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AchievementRepositoryTest {
    

    @Autowired 
    protected AchievementRepository repo;

    @Test
    public void testFindAll(){
        List<Achievement> achievements = repo.findAll();
        assertNotNull(achievements);
        assertFalse(achievements.isEmpty());
    }

    @Test
    public void testFindById(){
        Optional<Achievement> achievementOpt = repo.findById(1);
        assertTrue(achievementOpt.isPresent());
        achievementOpt = repo.findById(0);
        assertFalse(achievementOpt.isPresent());
    }
    @Test
    public void testFindAllMyAchievements(){
        List<Achievement> myAchievements = repo.findAllMyAchievements(1);
        assertFalse(myAchievements.isEmpty());
        myAchievements=repo.findAllMyAchievements(2);
        assertTrue(myAchievements.isEmpty());
    }

    @Test
    public void testDeleteUsersAchievementsUserToDelete(){
        List<Achievement> myAchievementsBefore = repo.findAllMyAchievements(1);
        assertFalse(myAchievementsBefore.isEmpty());

        repo.deleteUsersAchievementsUserToDelete(1);

        List<Achievement> myAchievementsAfter = repo.findAllMyAchievements(1);
        assertTrue(myAchievementsAfter.isEmpty());
    }
}
