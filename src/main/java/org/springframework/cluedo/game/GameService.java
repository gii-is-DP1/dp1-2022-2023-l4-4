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
    
    @Transactional(readOnly = true)
	public List<Game> findAllGames() throws DataAccessException {
		return gameRepository.findAll();
	}
}
