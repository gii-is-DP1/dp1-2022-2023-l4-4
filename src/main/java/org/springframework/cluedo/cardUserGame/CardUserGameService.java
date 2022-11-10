package org.springframework.cluedo.cardUserGame;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardUserGameService {
    
    private CardUserGameRepository cardUserGameRepository;
    @Autowired
    private CardUserGameService (CardUserGameRepository cardUserGameRepository){
        this.cardUserGameRepository=cardUserGameRepository;
    }

    @Transactional
    public Set<Card> doYouHaveCards(Set<Integer> cards,UserGame userGame){
        return cardUserGameRepository.doYouHaveCards(cards, userGame);
    }

}
