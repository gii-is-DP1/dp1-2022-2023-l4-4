package org.springframework.cluedo.cardUserGame;

import java.util.Set;

import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.user.UserGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardUserGameRepository extends CrudRepository<CardUserGame,Integer>{
    
    @Query("SELECT cug.card FROM CardUserGame cug WHERE cug.userGame.gameId=:userGame.gameId AND cug.userGame.userId=:userGame.userId AND cug.card.cardId IN :cards")
    Set<Card> doYouHaveCards(@Param("cardIds") Set<Integer> cards, @Param("userGame") UserGame userGame);

}
