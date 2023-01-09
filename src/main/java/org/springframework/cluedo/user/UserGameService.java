package org.springframework.cluedo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.game.Game;
import org.springframework.stereotype.Service;

@Service
public class UserGameService {
    UserGameRepository userGameRepository;
    AccusationService accusationService;

    @Autowired
    public UserGameService (UserGameRepository userGameRepository, AccusationService accusationService){
        this.userGameRepository = userGameRepository;
        this.accusationService = accusationService;
    }
    
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

    public UserGame getUsergameByGameAndUser(Game game, User user) {
        return userGameRepository.findUsergameByGameAndUser(game,user);
    }
    
    public Optional<UserGame> getNextUsergame(Game game){
        return userGameRepository.findPlayerByGameAndOrder(game,game.getActualPlayer().getOrderUser()+1);
    }

    public List<UserGame> remainingPlayersNotEliminated(Game game){
        return userGameRepository.remainingPlayersNotEliminated(game);
    }

    public UserGame whoShouldGiveCard(Game game, Accusation accusation) {
        UserGame accusatorUser= accusation.getTurn().getUserGame();
        UserGame next = getUsergameByGameAndOrder(game, (accusatorUser.getOrderUser()%game.getPlayers().size())+1);
        while (!next.equals(accusatorUser)){
            List<Card> cards=accusationService.getMatchingCardsFromUser(accusation, next);
            if (cards.size()!=0){
                return next;
            }else{
                next=getUsergameByGameAndOrder(game, (next.getOrderUser()%game.getPlayers().size())+1);
            }
        }
        return null;
    }

}
