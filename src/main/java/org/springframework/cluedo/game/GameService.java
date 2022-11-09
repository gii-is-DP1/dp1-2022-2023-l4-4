package org.springframework.cluedo.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class GameService {

    private GameRepository gameRepository;

    @Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
    //Admin
	//H12
    @Transactional(readOnly = true)
	public List<Game> findAllActiveGames() throws DataAccessException {
		return gameRepository.findAllActiveGames();
	}
	//H13
	@Transactional(readOnly=true)
	public List<Game> findAllPastGames(){
		return gameRepository.findAllPastGames();
	}
	
	//User
	//H10
	@Transactional(readOnly=true)
	public List<Game> findAllActivePublicGames(){
		return gameRepository.findAllActivePublicGames();
	}
	//H11
	@Transactional(readOnly=true)
	public List<Game> findAllPastUserGames(Integer userId) {
		return gameRepository.findAllPastGames(userId);
	} 
	//H1
	@Transactional()
	public void saveGame(Game game){
		gameRepository.save(game);
	}
}
