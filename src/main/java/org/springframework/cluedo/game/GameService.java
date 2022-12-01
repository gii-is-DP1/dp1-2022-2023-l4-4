package org.springframework.cluedo.game;

import java.time.Duration;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.exceptions.CorruptGame;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
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

	@Autowired
	public GameService(GameRepository gameRepository, TurnService turnService, CardService cardService, UserService userService) {
		this.gameRepository = gameRepository;
		this.turnService = turnService;
		this.cardService = cardService;
		this.userService = userService;
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
		cardService.initCards(copy);
	} 

	public void initTurn(Game game){
        Integer playerCount;
        if(game.getActualPlayer()==null){
            playerCount= -1; 
        }else{
            playerCount = game.getPlayers().indexOf(game.getActualPlayer());
        }
         if (game.getPlayers().size()-1 == playerCount){
             game.setActualPlayer(game.getPlayers().get(0));
             game.setRound(game.getRound()+1);
        }else{
            game.setActualPlayer(game.getPlayers().get(playerCount+1));
        }
        saveGame(game);
        turnService.createTurn(game.getActualPlayer(),game.getRound());
	}

	public void moveTo(Game game,Celd finalCeld) throws CorruptGame,WrongPhaseException{
		Optional<Turn> nrTurn=turnService.getTurn(game.getActualPlayer(), game.getRound());
        if(nrTurn.isPresent()){
			Turn turn=nrTurn.get();
			turnService.moveCharacter(turn, finalCeld);
		}else{
            throw new CorruptGame();
        }
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
}
	
