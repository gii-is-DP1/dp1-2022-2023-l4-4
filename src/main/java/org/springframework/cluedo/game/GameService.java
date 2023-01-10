package org.springframework.cluedo.game;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.accusation.FinalAccusation;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;import org.springframework.cluedo.exceptions.DataNotFound;
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
	private AccusationService accusationService;

	@Autowired
	public GameService(GameRepository gameRepository, TurnService turnService, CardService cardService, UserService userService, UserGameService userGameService, AccusationService accusationService) {
		this.gameRepository = gameRepository;
		this.turnService = turnService;
		this.cardService = cardService;
		this.userService = userService;
		this.userGameService = userGameService;
		this.accusationService = accusationService;
	}
    
    @Transactional(readOnly = true)
	public List<Game> getAllNotFinishedGames() throws DataAccessException {
		return gameRepository.findAllNotFinishedGames();
	}
	
	@Transactional(readOnly=true)
	public List<Game> getAllFinishedGames(){
		return gameRepository.findAllFinishedGames();
	}

	@Transactional(readOnly=true)
	public List<Game> getAllPublicLobbies(){
		return gameRepository.findAllPublicLobbies();
	}
	
	@Transactional(readOnly=true)
	public List<Game> getMyFinishedGames(User user) {
		return gameRepository.findMyFinishedGames(user);
	}

	public void initGame(Game copy){
        copy.setStatus(Status.IN_PROGRESS);
        copy.setStartTime(Timestamp.from(Instant.now()));
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

	@Transactional
	public void saveGame(Game game){
		gameRepository.save(game);
	}

	@Transactional
	public void deleteGame(Game game){
		gameRepository.delete(game);
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
			if(game.getActualPlayer().getOrderUser()==game.getPlayers().size()){
				game.setRound(game.getRound()+1);
				game.setActualPlayer(userGameService.getFirstUsergame(game)); 
			}else{ 
				game.setActualPlayer(userGameService.getNextUsergame(game).get());  
			}
		}while(game.getActualPlayer().getIsEliminated()==true);
		saveGame(game);
		turnService.createTurn(game.getActualPlayer(),game.getRound());
	}

	/*public void makeAccusation(Game game, Accusation accusation){
		accusationService.saveAccusation(accusation);
        turnService.makeAccusation(game);
	}*/

	public void makeFinalAccusation(Game game, FinalAccusation finalAccusation) throws WrongPhaseException {
		try{
			Turn actualTurn=turnService.getActualTurn(game).get();
			turnService.makeFinalDecision(actualTurn);
			accusationService.saveFinalAccusation(finalAccusation);
			finalAccusation.setCorrect(accusationService.isFinalAccusationCorrect(actualTurn));
			accusationService.saveFinalAccusation(finalAccusation);
			if (finalAccusation.isCorrect()) {
				finishGame(finalAccusation);
			}else{
				UserGame usergame=actualTurn.getUserGame();
				usergame.setIsEliminated(true);
				userGameService.saveUserGame(usergame);
				if(userGameService.remainingPlayersNotEliminated(game).size()==0){
					finishGame(finalAccusation);
				}else{
					finishTurn(game);
				}
			}
			
		}catch(WrongPhaseException e){
			throw e;
		}
	}

	public void finishGame(FinalAccusation finalAccusation){
		Game game=finalAccusation.getTurn().getUserGame().getGame();
		if(finalAccusation.isCorrect()){
			game.setWinner(finalAccusation.getTurn().getUserGame().getUser());	
		}
		game.setEndTime(Timestamp.from(Instant.now()));
		game.setStatus(Status.FINISHED);
		saveGame(game);

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
        if(!game.getHost().equals(user)){
            game.removeLobbyUser(user);
            saveGame(game);
        } else {
            deleteGame(game);
        } 
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

	
