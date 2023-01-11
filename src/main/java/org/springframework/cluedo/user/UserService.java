
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
import org.springframework.cluedo.achievement.AchievementRepository;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameRepository;
import org.springframework.cluedo.message.MessageRepository;
import org.springframework.cluedo.notification.NotificationRepository;
import org.springframework.cluedo.statistics.UserStatisticsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserGameService userGameService;
	private UserStatisticsRepository userStatisticsRepository;
	private UserGameRepository userGameRepository;
	private GameRepository gameRepository;
	private MessageRepository messageRepository;
	private NotificationRepository notificationRepository;
	private AchievementRepository achievementRepository;
	
	@Autowired
	public UserService(UserRepository userRepository,UserGameService userGameService,
	UserStatisticsRepository userStatisticsRepository,UserGameRepository userGameRepository,
	GameRepository gameRepository,MessageRepository messageRepository,NotificationRepository notificationRepository,
	AchievementRepository achievementRepository) {
		this.userRepository = userRepository;
		this.userGameService = userGameService;
		this.userStatisticsRepository = userStatisticsRepository;
		this.userGameRepository = userGameRepository;
		this.gameRepository=gameRepository;
		this.messageRepository=messageRepository;
		this.notificationRepository = notificationRepository;
		this.achievementRepository = achievementRepository;
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers(){
		
		return userRepository.findAll();
	}
	@Transactional(readOnly = true)
	public List<User> getXUsers(int page){
		Integer normal =3;
		Integer min= normal*page;
		Integer max= min + normal;

		if(page>=0){
			if(min >= userRepository.findAll().size()){
				min= userRepository.findAll().size();
				max= userRepository.findAll().size();
			}
			if(max >= userRepository.findAll().size()){
				max= userRepository.findAll().size();
			}
		List<User> users = userRepository.findAll().subList(min, max);
			return users;
		}
		else{
			return null;
		}
		
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
	public User findUserByTag(String tag) {
		return userRepository.findByTag(tag);
	}

	@Transactional
	public void deleteUser(int id){
		userStatisticsRepository.deleteUserStatisticByUserId(id);    
		userGameRepository.setNullUser(id);
		gameRepository.setWinnerNull(id);
		gameRepository.setHostNull(id);
		gameRepository.deleteUserInLobby(id); 
		notificationRepository.deleteNotificactionsByReceiverId(id);
		notificationRepository.deleteNotificationsBySenderId(id);
		messageRepository.setNullUser(id);
		userRepository.deleteUserFriendsUserToDelete(id);
		achievementRepository.deleteUsersAchievementsUserToDelete(id);
		userRepository.deleteUser(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional(readOnly = true)
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
	public void addFriend(User user) {
		User loggedUser = this.getLoggedUser().get();
		loggedUser.addFriend(user);
		this.saveUser(loggedUser);
	}
	@Transactional
	public void deleteFriend(User user) {
		User loggedUser = this.getLoggedUser().get();
		loggedUser.deleteFriend(user);
		this.saveUser(loggedUser);
	}

	@Transactional(readOnly = true)
	public String generarTag(){
		//La variable palabra almacena el resultado final 
			String palabra = "#"; 
		//La variable caracteres es un número aleatorio entre 2 y 20 que define la 
		//longitud de la palabra. 
			int caracteres = 4; 
		//Con un bucle for, que recorreremos las veces que tengamos almacenadas en la 
		//variable caracteres, que será como mínimo 2, iremos concatenando los 
		//caracteres aleatorios. 
				 for (int i=0; i<caracteres; i++){ 
		//Para generar caracteres aleatorios hay que recurrir al valor numérico de estos 
		//caracteres en el alfabeto Ascii. En este programa vamos a generar palabras con 
		//letras minúsculas, que se encuentran en el rango 65-90. El método floor 
		//devuelve el máximo entero. 
				 int codigoAscii = (int)Math.floor(Math.random()*(90 -
				 65)+65); 
		//para pasar el código a carácter basta con hacer un cast a char 
				 palabra = palabra + (char)codigoAscii; 
				 } 
				 String numero = (ThreadLocalRandom.current().nextInt(8)+1)+"";
				 String result = palabra + numero+numero+numero+numero;
				 List<String> allTags = userRepository.findAllTags();
				 if(allTags.contains(result)){
					result = generarTag();
				 }
				 return result; 
			 } 
}
