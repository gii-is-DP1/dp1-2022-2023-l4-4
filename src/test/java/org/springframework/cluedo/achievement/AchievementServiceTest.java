package org.springframework.cluedo.achievement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.achievement.AchievementService;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {
    
    @Mock
    protected AchievementRepository repo;

    List<Achievement> achievements = new ArrayList<>();
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
    }

    @Test
    public void shouldFindAllAchievements(){
        when(repo.findAll()).thenReturn(achievements);
        List<Achievement> test = repo.findAll();
        assertNotNull(test);
        assertTrue(test.size()==1);
    }

    @Test
    public void shouldFindAchievementById(){
        when(repo.findById(anyInt())).thenReturn(Optional.of(achievements.get(0)));
        Optional<Achievement> achievement = repo.findById(1);
        assertNotNull(achievement);
        assertTrue(achievement.isPresent());
        assertTrue(achievement.get().getId()==1);
    }

}
