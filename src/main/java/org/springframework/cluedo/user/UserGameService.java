package org.springframework.cluedo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.game.Game;
import org.springframework.stereotype.Service;

@Service
public class UserGameService {
    @Autowired
    UserGameRepository userGameRepository;
    
    public UserGame saveUserGame(UserGame userGame) {
        return userGameRepository.save(userGame);
    }

    public UserGame getUsergameByGameAndOrder(Game game, Integer order){
        return userGameRepository.findPlayerByGameAndOrder(game, order).get();
    }

    public UserGame getFirstUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,1).get();
    }

    
    public Optional<UserGame> getNextUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,game.getActualPlayer().getOrderUser()+1);
    }

    public List<UserGame> remainingPlayersNotEliminated(Game game){
        return userGameRepository.remainingPlayersNotEliminated(game);
    }

}
