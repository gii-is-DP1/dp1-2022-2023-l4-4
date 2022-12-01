package org.springframework.cluedo.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.accusation.CrimeScene;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameService;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    CardRepository cardRepository;
    UserGameService userGameService;
    AccusationService accusationService;
    
    @Autowired
    public CardService(CardRepository cardRepository, UserGameService userGameService, AccusationService accusationService){
        this.cardRepository = cardRepository;
        this.userGameService = userGameService;
        this.accusationService = accusationService;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    
    public void initCards(Game game) {
        List<Card> weapons = cardRepository.findWeapons();
        List<Card> suspects = cardRepository.findSuspects();
        List<Card> rooms = cardRepository.findRooms();
        CrimeScene crimeScene = new CrimeScene();
        crimeScene.setWeaponCard(weapons.get(ThreadLocalRandom.current().nextInt(weapons.size())));
        crimeScene.setSuspectCard(suspects.get(ThreadLocalRandom.current().nextInt(suspects.size())));
        crimeScene.setRoomCard(rooms.get(ThreadLocalRandom.current().nextInt(rooms.size())));
        accusationService.saveCrimeScene(crimeScene);
        game.setCrimeScene(crimeScene);
        List<Card> crimeSceneCards = crimeScene.getCards();
        List<Card> remainingCards = Stream.of(weapons,suspects,rooms).flatMap(Collection::stream).collect(Collectors.toList());
        remainingCards.removeAll(crimeSceneCards);
        List<UserGame> players = game.getPlayers();
        int i=0;
        while (remainingCards.size()>0){
            if(i==players.size()){
                i=0;
            }
            Card card=remainingCards.get(ThreadLocalRandom.current().nextInt(remainingCards.size()));
            UserGame player = players.get(i);
            player.addCard(card);
            players.set(i,player);
            remainingCards.remove(card);
            i++;
        }
        for (UserGame player:players){ 
            userGameService.saveUserGame(player);
        }
    }
}
