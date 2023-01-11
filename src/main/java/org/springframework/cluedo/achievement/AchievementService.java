package org.springframework.cluedo.achievement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.cluedo.statistics.UserStatisticsRepository;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
    
    private AchievementRepository achievementRepository;
    private UserStatisticsRepository userStatisticsRepository;
    private UserService userService;
	private UserStatisticsService userStatisticsService;

    @Autowired
    public AchievementService(AchievementRepository repo, UserService userService,UserStatisticsService userStatisticsService,
	UserStatisticsRepository userStatisticsRepository){
        this.achievementRepository = repo;
        this.userService=userService;
		this.userStatisticsService = userStatisticsService;
		this.userStatisticsRepository = userStatisticsRepository;
    }

    @Transactional(readOnly=true)
    public List<Achievement> getAllAchievements(){
        return achievementRepository.findAll();
    }
    
    @Transactional
    public void saveAchievement(Achievement achievement) {
        achievementRepository.save(achievement);
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementById(Integer achievementId) {
        return achievementRepository.findById(achievementId).orElse(null);
    }

    @Transactional
	private void obtainAchievement(Achievement achievement, User user){
		user.addAchievement(achievement);
		userStatisticsService.updateExpStatistics(user, achievement);
		userService.saveUser(user);
	}

	@Transactional
	public void checkToObtainAchievement(Achievement achievement, User user){
		UserStatistics stats = userStatisticsRepository.findMyStatistics(user);
		if(!user.getAchievements().contains(achievement)){
			switch(achievement.getMetric()){
				case VICTORIES:
					if(stats.getVictories()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case EXPERIENCE:
					if(stats.getXp()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case TOTAL_GAMES:
					if(stats.getTotalGames()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case TOTAL_ROUNDS:
					if(stats.getTotalRounds()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case TOTAL_TIME:
					if(stats.getTotalTime()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case TOTAL_ACUSATIONS:
					if(stats.getTotalAccusations()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
				case TOTAL_FINAL_ACUSATIONS:
					if(stats.getTotalFinalAccusations()>=achievement.getGoal()){
						obtainAchievement(achievement, user);
						break;
					}
			}
		}
	}

    @Transactional(readOnly = true)
	    public List<Achievement> findAllMyAchievements(){
		    User loggedUser = userService.getLoggedUser().get();
		return achievementRepository.findAllMyAchievements(loggedUser.getId());
	}

    public void checkAchievementsGame(Game game) {
		List<UserGame> userGames=game.getPlayers();
		List<Achievement> achievements = getAllAchievements();
		for (UserGame userGame:userGames){
			for (Achievement achievement : achievements){
				checkToObtainAchievement(achievement, userGame.getUser());
			}
		}
	}
}