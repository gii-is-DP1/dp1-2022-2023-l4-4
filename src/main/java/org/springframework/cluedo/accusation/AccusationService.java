package org.springframework.cluedo.accusation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.game.Game;
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

    public List<Accusation> getAllAcusationsByGame(Game game){
        return accusationRepository.findAllByGame(game.getId());
    }

    public Boolean isFinalAccusationCorrect(Turn turn){
        CrimeScene crimeScene = crimeSceneRepository.findByGame(turn.getUserGame().getGame().getId());
        FinalAccusation finalAccusation = finalAccusationRepository.findByTurn(turn.getId());
        if(crimeScene.getCards().containsAll(finalAccusation.getCards())){
            return true;
        }else{
            return false;
        }
    }

    public Optional<Accusation> thisTurnAccusation(Turn turn){
        return accusationRepository.findByTurn(turn.getId());
    }

    public FinalAccusation saveFinalAccusation(FinalAccusation finalAccusation) {
        return finalAccusationRepository.save(finalAccusation);
    }

	public void showCard(Turn turn, Card card) {
        Accusation accusation=thisTurnAccusation(turn).get();
        accusation.setShownCard(card);
        saveAccusation(accusation);
	}

}
