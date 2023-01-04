package org.springframework.cluedo.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatisticsService {
    
   
    private UserStatisticsRepository userStatisticsRepository;
    private UserService userService;

    @Autowired
    public UserStatisticsService(UserStatisticsRepository userStatisticsRepository, UserService userService){
        this.userStatisticsRepository=userStatisticsRepository;
        this.userService=userService;
    }

    @Transactional
    public void save(UserStatistics statistics) throws DataAccessException{
        userStatisticsRepository.save(statistics);
    }

    @Transactional(readOnly=true)
    public List<UserStatistics> getAllStatistics(){
        return userStatisticsRepository.findAll();
    }
 
    @Transactional(readOnly = true)
	public UserStatistics getMyStatistics(){
		User loggedUser =userService.getLoggedUser().get();
		return userStatisticsRepository.findMyStatistics(loggedUser);
	}

    @Transactional
    public void updateStatistics(Game game, User user){
        UserGame userGame = game.getPlayers().stream().filter(x -> x.getUser().equals(user)).findFirst().get();
        UserStatistics stats = userStatisticsRepository.findMyStatistics(user);
        if(game.getWinner().equals(user)) stats.setVictories(stats.getVictories()+1);
        if(userGame.getIsAfk()) stats.setAfkCounter(stats.getAfkCounter()+1);
        stats.setTotalGames(stats.getTotalGames()+1);
        stats.setTotalRounds(stats.getTotalRounds()+game.getRound());
        if(game.getWinner().equals(user) || userGame.getIsEliminated()) 
            stats.setTotalFinalAccusations(stats.getTotalAccusations()+1);
        stats.setTotalAccusations(stats.getTotalAccusations()+ userGame.getAccusationsNumber());
        if(stats.getLongestGame()==null && stats.getShortestGame()==null){
            stats.setLongestGame(game);
            stats.setShortestGame(game);
        } else {
            if(stats.getLongestGame().getDuration().compareTo(game.getDuration())<0){
                stats.setLongestGame(game);
            }
            if(stats.getShortestGame().getDuration().compareTo(game.getDuration())>0){
                stats.setShortestGame(game);
            }
        }
        save(stats);
    }

    @Transactional
    public void updateExpStatistics(User user, Achievement achievement){
        UserStatistics stats = userStatisticsRepository.findMyStatistics(user);
        stats.setXp(stats.getXp()+achievement.getXp());
        save(stats);
    }
}
