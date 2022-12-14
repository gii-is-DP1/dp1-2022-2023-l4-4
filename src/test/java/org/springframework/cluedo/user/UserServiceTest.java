package org.springframework.cluedo.user;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
    
    @Mock
    UserRepository repo;

    User user;
    List<User> users = new ArrayList<>();
    List<Achievement> achievements = new ArrayList<>();
    UserStatistics stats = new UserStatistics();
    @BeforeEach
    public void config(){

        Achievement achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("achievement1");
        achievement.setXp(100);
        achievement.setMetric(Metric.EXPERIENCE);
        achievement.setDescription("Achievement for test");
        achievement.setGoal(1000);
        achievement.setBadgeType(Badge.BRONZE);
        achievements.add(achievement);

        user=new User();
        user.setUsername("user1");
        user.setPassword("user1");
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user.setAuthority("user");
        user.setEnabled(1);
        users.add(user);

        user.setUsername("user2");
        user.setPassword("user2");
        user.setId(2);
        user.setEmail("user2@gmail.com");
        user.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user.setAuthority("admin");
        user.setEnabled(1);
        users.add(user); 

        stats.setId(1);
        stats.setUser(user);
        stats.setVictories(20);

    }
    
    @Test
    void getAllUsers() {
        when(repo.findAll()).thenReturn(users);
        List<User> allUsers=repo.findAll();
        assertFalse(allUsers.isEmpty());
        assertTrue(allUsers.size()==2);
    }


    @Test
    void findUserById() {
        when(repo.findById(any(Integer.class))).thenReturn(Optional.of(user));
   
        Optional<User> user = repo.findById(1);
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("admin"));
        assertFalse(user.get().getImageurl().isEmpty());
    }

    @Test
    void findUserByUsername() {
        when(repo.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        Optional<User> user = repo.findByUsername("user2");
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("admin"));
        assertFalse(user.get().getImageurl().isEmpty());
    }
    
    @Test
    void findAllMyAchievements(){
        when(repo.findAllMyAchievements(any(Integer.class))).thenReturn(achievements);
        List<Achievement> achievements = repo.findAllMyAchievements(2);
        assertNotNull(achievements);
        assertTrue(achievements.size()==1);
        assertTrue(achievements.get(0).getName().equals("achievement1"));
    }

    @Test
    void getMyStatistics(){
        when(repo.findMyStatistics(any(User.class))).thenReturn(stats);
        UserStatistics stats = repo.findMyStatistics(user);
        assertNotNull(stats);
        assertTrue(stats.getUser().equals(user));
        assertTrue(stats.getVictories()==20);
        assertTrue(stats.getUser().getId()==2);
        assertTrue(stats.getId()==1);
    }

}