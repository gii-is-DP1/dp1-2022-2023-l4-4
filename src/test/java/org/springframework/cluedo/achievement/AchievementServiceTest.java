package org.springframework.cluedo.achievement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.stereotype.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.achievement.AchievementService;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTest {
    
    @Autowired
    protected AchievementService service;


    @Test
    public void shouldFindAchievementById(){
        Achievement achievement = this.service.getAchievementById(1);
        assertNotNull(achievement);
    }
}
