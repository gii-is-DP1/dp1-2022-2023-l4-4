package org.springframework.cluedo.achievement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
    
    private AchievementRepository repo;
    
    @Autowired
    public AchievementService(AchievementRepository repo){
        this.repo = repo;
    }

    @Transactional(readOnly=true)
    public List<Achievement> getAllAchievements(){
        return repo.findAll();
    }
    
    @Transactional
    public void saveAchievement(Achievement achievement) {
        repo.save(achievement);
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementById(Integer achievementId) {
        return repo.findById(achievementId).orElse(null);
    }

}
