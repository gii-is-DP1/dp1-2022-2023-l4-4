package org.springframework.cluedo.card;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer>{
    List<Card> findAll();

    @Query("SELECT c FROM Card c WHERE c.cardType = org.springframework.cluedo.enumerates.CardType.WEAPON")
    List<Card> findWeapons();
    @Query("SELECT c FROM Card c WHERE c.cardType = org.springframework.cluedo.enumerates.CardType.ROOM")
    List<Card> findRooms();
    @Query("SELECT c FROM Card c WHERE c.cardType = org.springframework.cluedo.enumerates.CardType.SUSPECT")
    List<Card> findSuspects();
}
