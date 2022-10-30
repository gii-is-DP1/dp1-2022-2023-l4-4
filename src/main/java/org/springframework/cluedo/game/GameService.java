package org.springframework.cluedo.game;

import java.util.List;

public class GameService {
    
    private GameRepository gameRepository;

    public List<Game> findGames() {
		return gameRepository.findAll();
    }
}
