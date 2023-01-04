package org.springframework.cluedo.game;

import java.time.Duration;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private GameRepository gameRepository;
	private TurnService turnService;
	private CardService cardService;
	private UserService userService;
	private UserGameService userGameService;

	@Autowired
	public GameService(GameRepository gameRepository, TurnService turnService, CardService cardService, UserService userService, UserGameService userGameService) {
		this.gameRepository = gameRepository;
		this.turnService = turnService;
		this.cardService = cardService;
		this.userService = userService;
		this.userGameService = userGameService;
	}
    //Admin
	//H12
    @Transactional(readOnly = true)
	public List<Game> getAllNotFinishedGames() throws DataAccessException {
		return gameRepository.findAllNotFinishedGames();
	}
	//H13
	@Transactional(readOnly=true)
	public List<Game> getAllFinishedGames(){
		return gameRepository.findAllFinishedGames();
	}

	
	
	//User
	//H10
	@Transactional(readOnly=true)
	public List<Game> getAllPublicLobbies(){
		return gameRepository.findAllPublicLobbies();
	}
	//H11
	@Transactional(readOnly=true)
	public List<Game> getMyFinishedGames(User user) {
		return gameRepository.findMyFinishedGames(user);
	}

	public void initGame(Game copy){
        copy.setStatus(Status.IN_PROGRESS);
        copy.setDuration(Duration.ofMinutes(0));
        copy.setRound(1);
		userService.initializePlayers(copy.getLobby(), copy);
		copy.setActualPlayer(copy.getPlayers().get(0));
		cardService.initCards(copy);
		
	} 

	public void initTurn(Game game){
		turnService.createTurn(game.getActualPlayer(),game.getRound());
	}


	public void moveTo(Game game,Celd finalCeld) throws WrongPhaseException{
		Turn turn=turnService.getTurn(game.getActualPlayer(), game.getRound()).get();
		turnService.moveCharacter(turn, finalCeld);
	}

	//H1
	@Transactional
	public void saveGame(Game game){
		gameRepository.save(game);
	}

	@Transactional(readOnly=true)
	public Game getGameById(Integer gameId) throws DataNotFound{
		Optional<Game> game = gameRepository.findById(gameId);
		if(!game.isPresent()) {
			throw new DataNotFound();
		}
		return game.get();
	}

	public void finishTurn(Game game){
		Turn actualTurn=turnService.getActualTurn(game).get();
		actualTurn.setPhase(Phase.FINISHED);
		turnService.saveTurn(actualTurn);
		do{
			if(actualTurn.getUserGame()==game.getPlayers().get(game.getPlayers().size()-1)){
				game.setRound(game.getRound()+1);
				game.setActualPlayer(userGameService.getFirstUsergame(game)); 
			}else{
				game.setActualPlayer(userGameService.getNextUsergame(game).get());  
			}
		}while(game.getActualPlayer().getIsEliminated()==true);
		saveGame(game);
		turnService.createTurn(game.getActualPlayer(),game.getRound());
	}

	public boolean isUserTurn(Optional<User> user, Game game) {
		if (!user.isPresent() || !user.get().equals(game.getActualPlayer().getUser())){
			return false;
		}
		return true;
	}

	public boolean isGameInProgress(Game game) {
		if(game.getStatus().equals(Status.IN_PROGRESS)) {
			return true;
		}
		return false;
	}
	public Game getMyNotFinishedGame(User user) {
		return gameRepository.getMyNotFinishedGame(user);
	}
	public void deleteUserFromLobby(User user, Game game) {
		List<User> users = game.getLobby();
		users.remove(user);
		game.setLobby(users);
		saveGame(game);
	}
	public void leaveGameInProgress(User user, Game game) {
		List<UserGame> ugs = game.getPlayers();
		UserGame ug = ugs.stream().filter(x->x.getUser().equals(user)).findAny().orElse(null);
		if(ug!=null){
			ug.setIsEliminated(true);
			userGameService.saveUserGame(ug);
		}
	}
} 

	
