
package org.springframework.cluedo.user;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserGameService userGameService;
	

	@Autowired
	public UserService(UserRepository userRepository,UserGameService userGameService) {
		this.userRepository = userRepository;
		this.userGameService = userGameService;
	}

	@Transactional
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByTag(String tag) {
		return userRepository.findByTag(tag);
	}
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public List<User> findUserFriends(Integer id) {
		return userRepository.findFriendsById(id);
	}


	@Transactional(readOnly = true)
	public Optional<User> getLoggedUser(){
		UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}

	@Transactional(readOnly = true)
	public UserDetails getUserDetails(){
		return(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Transactional(readOnly = true)
	public List<Achievement> findAllMyAchievements(){
		User loggedUser = getLoggedUser().get();
		return userRepository.findAllMyAchievements(loggedUser.getId());
	}

	@Transactional(readOnly = true)
	public UserStatistics getMyStatistics(){
		User loggedUser = getLoggedUser().get();
		return userRepository.findMyStatistics(loggedUser);
	}

	@Transactional
    public void initializePlayers(List<User> lobby, Game copy) { 
		List<SuspectType> suspects= new ArrayList<>(Arrays.asList(SuspectType.values()));
		List<Integer> orderList= IntStream.rangeClosed(1, lobby.size())
		.boxed().collect(Collectors.toList()); 
		for (User user : lobby) {
			Integer available = suspects.size();
			UserGame userGame = new UserGame();
			userGame.setAccusationsNumber(0);
			userGame.setGame(copy);
			userGame.setUser(user);
			userGame.setIsAfk(false);
			userGame.setIsEliminated(false);
			Integer suspect = ThreadLocalRandom.current().nextInt(available);
			userGame.setSuspect(suspects.get(suspect));
			suspects.remove(suspects.get(suspect));
			userGame.setCards(new HashSet<Card>());
			Integer order = ThreadLocalRandom.current().nextInt(orderList.size());
			userGame.setOrderUser(orderList.get(order));
			orderList.remove(orderList.get(order));
			userGameService.saveUserGame(userGame);
			copy.addPlayers(userGame);
		}
		copy.setPlayers(copy.getPlayers().stream().sorted(Comparator.comparing(x->x.getOrderUser())).collect(Collectors.toList()));
    }
	
	@Transactional
	private void obtainAchievement(Achievement achievement){
		User loggedUser = getLoggedUser().get();
		loggedUser.addAchievement(achievement);
		userRepository.save(loggedUser);
	}

	@Transactional
	public void checkToObtainAchievement(Achievement achievement){
		User loggedUser = getLoggedUser().get();
		UserStatistics stats = userRepository.findMyStatistics(loggedUser);
		if(!loggedUser.getAchievements().contains(achievement)){
			switch(achievement.getMetric()){
				case VICTORIES:
					if(stats.getVictories()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case EXPERIENCE:
					if(stats.getXp()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case TOTAL_GAMES:
					if(stats.getTotalGames()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case TOTAL_ROUNDS:
					if(stats.getTotalRounds()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case TOTAL_TIME:
					if(stats.getTotalTime()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case TOTAL_ACUSATIONS:
					if(stats.getTotalAccusations()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
				case TOTAL_FINAL_ACUSATIONS:
					if(stats.getTotalFinalAccusations()>=achievement.getGoal()){
						obtainAchievement(achievement);
					}
			}
		}
		
	}
}
