package org.springframework.cluedo.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private GameRepository gameRepository;
	@Autowired
	public GameService(GameRepository gameRepository, UserService userService) {
		this.gameRepository = gameRepository;
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
		//return StreamSupport.stream(gameRepository.findAllById(gameRepository.findMyFinishedGames(userId)).spliterator(),false).collect(Collectors.toList());
		//gameRepository.findAllById(gameRepository.findMyFinishedGames(userId)).forEach(x->res.add(x));
		return gameRepository.findMyFinishedGames(user);
	} 
	//H1
	@Transactional
	public void saveGame(Game game){
		gameRepository.save(game);
	}

	@Transactional(readOnly=true)
	public Optional<Game> getGameById(Integer gameId){
		return gameRepository.findById(gameId);
	}
}
