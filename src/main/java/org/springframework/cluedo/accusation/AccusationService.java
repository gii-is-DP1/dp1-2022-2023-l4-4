package org.springframework.cluedo.accusation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;

@Service
public class AccusationService {
    private AccusationRepository accusationRepository;
    private CrimeSceneRepository crimeSceneRepository;
    private FinalAccusationRepository finalAccusationRepository;

    @Autowired
    public AccusationService(AccusationRepository accusationRepository, CrimeSceneRepository crimeSceneRepository,FinalAccusationRepository finalAccusationRepository) {
        this.accusationRepository = accusationRepository;
        this.crimeSceneRepository = crimeSceneRepository;
        this.finalAccusationRepository = finalAccusationRepository;
    }
    
    public Accusation saveAccusation(Accusation accusation) {
        return accusationRepository.save(accusation);
    }

    public CrimeScene saveCrimeScene(CrimeScene crimeScene) {
        return crimeSceneRepository.save(crimeScene);
    }

    public List<Card> getMatchingCardsFromUser(Accusation accusation, UserGame user) {
        List<Card> matchingCards = new ArrayList<Card>();
        List<Card> accusationCards = List.of(accusation.getRoomCard(), accusation.getSuspectCard(),accusation.getWeaponCard());
        Set<Card> userCards = user.getCards();
        for(Card card:accusationCards) {
            if(userCards.contains(card)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

    public Boolean isFinalAccusationCorrect(Turn turn){
        CrimeScene crimeScene = crimeSceneRepository.findByGame(turn.getUserGame().getGame());
        FinalAccusation finalAccusation = finalAccusationRepository.findByTurn(turn);
        if(crimeScene.getCards().containsAll(finalAccusation.getCards())){
            return true;
        }else{
            return false;
        }
    }

    public FinalAccusation saveFinalAccusation(FinalAccusation finalAccusation) {
        return finalAccusationRepository.save(finalAccusation);
    }

}
