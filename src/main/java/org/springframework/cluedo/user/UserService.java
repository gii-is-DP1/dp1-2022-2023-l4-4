
package org.springframework.cluedo.user;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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

	@Transactional
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
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
		for (User user : lobby) {
			Integer available = suspects.size();
			UserGame userGame = new UserGame();
			userGame.setAccusationsNumber(0);
			userGame.setGame(copy);
			userGame.setUser(user);
			userGame.setIsAfk(false);
			Integer randomInt = ThreadLocalRandom.current().nextInt(available);
			userGame.setSuspect(suspects.get(randomInt));
			suspects.remove(suspects.get(randomInt));
			userGame.setCards(new HashSet<Card>());
			userGameService.saveUserGame(userGame);
			copy.addPlayers(userGame);
		}
    }

}
