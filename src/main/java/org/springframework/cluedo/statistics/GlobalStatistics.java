package org.springframework.cluedo.statistics;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalStatistics {
   
    private List<Game> allGames;
    private List<UserStatistics> globalStatistics;
    public GlobalStatistics(List<Game> allGames,List<UserStatistics> globalStatistics){
        this.allGames = allGames;
        this.globalStatistics=globalStatistics;
    }

    public Integer totalGames(){
        return allGames.size();
    }
    public Integer totalAccusations(){
        return globalStatistics.stream().mapToInt(UserStatistics::getTotalAccusations).sum();
    }
    public Integer totalFinalAccusations(){
        return globalStatistics.stream().mapToInt(UserStatistics::getTotalFinalAccusations).sum();
    }
    public Double averageDuration(){
        List<Duration> gamesDurationNotNull = allGames.stream().map(Game::getDuration)
        .filter(x -> x!=null).collect(Collectors.toList());
        if(gamesDurationNotNull.isEmpty()) return 0.;
        return gamesDurationNotNull.stream().mapToLong(Duration::toMinutes).average().getAsDouble();
    }
    public List<User> top3Wins(){
        return globalStatistics.stream()
        .sorted(Comparator.comparing(UserStatistics::getVictories).reversed())
        .map(UserStatistics::getUser)
        .collect(Collectors.toList());
    }
    
}
