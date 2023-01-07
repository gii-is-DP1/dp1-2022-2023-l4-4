package org.springframework.cluedo.achievement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.cluedo.statistics.UserStatisticsRepository;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {

    @Mock
    protected AchievementRepository achievementRepository;

    protected AchievementService achievementService;

    @Mock
    protected UserService userService;

    @Mock
    protected UserStatisticsService userStatisticsService;

    @Mock
    protected UserStatisticsRepository userStatisticsRepository;

    List<Achievement> achievements = new ArrayList<>();
    User user;
    UserStatistics stats;

    @BeforeEach
    public void config(){

        Achievement achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("Test");
        achievement.setGoal(5);
        achievement.setDescription("Test example");
        achievement.setMetric(Metric.TOTAL_GAMES);
        achievement.setXp(100);
        achievement.setBadgeType(Badge.BRONZE);
        achievements.add(achievement);

        achievement = new Achievement();
        achievement.setId(2);
        achievement.setName("Test 2");
        achievement.setGoal(10);
        achievement.setDescription("Test example 2");
        achievement.setMetric(Metric.TOTAL_GAMES);
        achievement.setXp(100);
        achievement.setBadgeType(Badge.SILVER);
        achievements.add(achievement);

        user = new User();
        user.setId(1);
        user.setUsername("test user");
        user.setAuthority("user");
        user.setEnabled(1);
        user.setPassword("test");
        user.setTag("#WDFG5555");
        user.setAchievements(new ArrayList<>());
        user.setEmail("test@email.com");

        stats = new UserStatistics();
        stats.setId(1);
        stats.setTotalGames(6);
        stats.setUser(user);

        achievementService= new AchievementService(achievementRepository, userService, userStatisticsService, userStatisticsRepository);
    }

    @Test
    public void shouldGetAllAchievements(){
        when(achievementRepository.findAll()).thenReturn(achievements);

        List<Achievement> test = achievementService.getAllAchievements();
        assertNotNull(test);
        assertTrue(test.size()==2);
    }

    @Test
    public void shouldGetAchievementById(){
        when(achievementRepository.findById(anyInt())).thenReturn(Optional.of(achievements.get(0)));

        Achievement achievement = achievementService.getAchievementById(1);
        assertNotNull(achievement);
        assertTrue(achievement.getId()==1);
    }

    @Test
    void findAllMyAchievements(){
        when(achievementRepository.findAllMyAchievements(any(Integer.class))).thenReturn(achievements);
        when(userService.getLoggedUser()).thenReturn(Optional.of(user));

        List<Achievement> achievements = achievementService.findAllMyAchievements();
        assertNotNull(achievements);
        assertTrue(achievements.size()==2);
        assertTrue(achievements.get(0).getName().equals("Test"));
    }

    @Test
    void shouldObtainAchievement(){
        Mockito.doNothing().when(userStatisticsService).updateExpStatistics(any(User.class), any(Achievement.class));
        Mockito.doNothing().when(userService).saveUser(any(User.class));
        when(userStatisticsRepository.findMyStatistics(any(User.class))).thenReturn(stats);

        assertTrue(user.getAchievements().isEmpty());
        achievementService.checkToObtainAchievement(achievements.get(0), user);
        assertTrue(!user.getAchievements().isEmpty());
        verify(userStatisticsRepository, times(1)).findMyStatistics(user);
    }

    @Test
    void shouldNotObtainAchievement(){
        when(userStatisticsRepository.findMyStatistics(any(User.class))).thenReturn(stats);

        assertTrue(user.getAchievements().isEmpty());
        achievementService.checkToObtainAchievement(achievements.get(1), user);
        assertTrue(user.getAchievements().isEmpty());
        verify(userStatisticsRepository, times(1)).findMyStatistics(user);
    }

    @Test
    void shouldSaveAchievement(){
        Achievement achievementToSave = new Achievement();
        achievementToSave.setId(3);
        achievementToSave.setName("New Test");
        achievementToSave.setGoal(10);
        achievementToSave.setDescription("Test has changed");
        achievementToSave.setMetric(Metric.VICTORIES);
        achievementToSave.setXp(1000);
        achievementToSave.setBadgeType(Badge.BRONZE);

        achievementService.saveAchievement(achievementToSave);
        verify(achievementRepository,times(1)).save(achievementToSave);
    }
}