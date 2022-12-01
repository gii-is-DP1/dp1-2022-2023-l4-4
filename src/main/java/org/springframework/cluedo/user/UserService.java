
package org.springframework.cluedo.user;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Transactional
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}


	@Transactional
	public void saveUser(User user) throws DataAccessException {
		userRepository.save(user);
	}
	
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}

	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> getLoggedUser(){
		UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}

	public UserDetails getUserDetails(){
		return(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
    public void initializePlayers(List<User> lobby, Game copy) { 
		
		List<SuspectType> suspects= Arrays.asList(SuspectType.values());
		
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
			userGame.setCards(null);
			copy.getPlayers().add(userGame);
		}
    }
}
