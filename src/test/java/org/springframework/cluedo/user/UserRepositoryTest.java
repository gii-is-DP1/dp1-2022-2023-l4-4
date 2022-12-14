package org.springframework.cluedo.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.statistics.UserStatistics;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    UserRepository repo;

    @Test
    public void testFindAll(){
        List<User> users = repo.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testFindById(){
        Integer i = 1;
        Optional<User> user = repo.findById(i);
        assertTrue(user.isPresent());
        i--;
        user=repo.findById(i);
        assertFalse(user.isPresent());
    }
    
    @Test
    public void testFindByUsername(){
        Optional<User> user = repo.findByUsername("manuel333");
        assertTrue(user.isPresent());
        user=repo.findByUsername("0");
        assertFalse(user.isPresent());
    }

    @Test
    public void testDeleteById(){
        Optional<User> user = repo.findById(3);
        assertTrue(user.isPresent());
        repo.deleteById(user.get().getId());
        user=repo.findById(3);
        assertFalse(user.isPresent());
    }
    @Test
    public void testFindAllMyAchievements(){
        List<Achievement> myAchievements = repo.findAllMyAchievements(1);
        assertFalse(myAchievements.isEmpty());
        myAchievements=repo.findAllMyAchievements(2);
        assertTrue(myAchievements.isEmpty());
    }

    @Test
    public void testFindMyStatistics(){
        Optional<User> user = repo.findByUsername("manuel333");
        UserStatistics stats = repo.findMyStatistics(user.get());
        assertNotNull(stats);
    }
}
