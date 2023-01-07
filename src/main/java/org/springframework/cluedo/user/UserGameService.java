package org.springframework.cluedo.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.game.Game;
import org.springframework.stereotype.Service;

@Service
public class UserGameService {
    @Autowired
    UserGameRepository userGameRepository;
    AccusationService accusationService;
    
    public UserGame saveUserGame(UserGame userGame) {
        return userGameRepository.save(userGame);
    }

    public UserGame getUsergameByGameAndOrder(Game game, Integer order){
        return userGameRepository.findPlayerByGameAndOrder(game, order).get();
    }

    public UserGame getFirstUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,1).get();
    }

    public UserGame getLastUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,game.getPlayers().size()).get();
        

    }

    public Optional<UserGame> getNextUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,game.getActualPlayer().getOrderUser()+1);
    }

    public List<UserGame> remainingPlayersNotEliminated(Game game){
        return userGameRepository.remainingPlayersNotEliminated(game);
    }

    public UserGame whoShouldGiveCard(Game game, Accusation accusation) {
        UserGame accusatorUser= accusation.getTurn().getUserGame();
        UserGame next = getUsergameByGameAndOrder(game, accusatorUser.getOrderUser()+1%game.getPlayers().size());
        while (!next.equals(accusatorUser) && accusation.getPlayerWhoShows()==null){
            List<Card> cards=accusationService.getMatchingCardsFromUser(accusation, next);
            if (cards.size()!=0){
                return next;
            }else{
                next=getUsergameByGameAndOrder(game, next.getOrderUser()+1%game.getPlayers().size());
            }
        }
        return null;
    }

}
