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
}
