package org.springframework.cluedo.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    UserGameService userGameService;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public void initCards(Game game) {
        List<Card> weapons = cardRepository.findWeapons();
        List<Card> suspects = cardRepository.findSuspects();
        System.out.println("MIRA AQUI JIJIJI" + suspects);
        suspects.forEach(x->System.out.println(x.getCardName()));
        List<Card> rooms = cardRepository.findRooms();
        Accusation accusation = new Accusation();
        accusation.setWeaponCard(weapons.get(ThreadLocalRandom.current().nextInt(weapons.size())));
        accusation.setSuspectCard(suspects.get(ThreadLocalRandom.current().nextInt(suspects.size())));
        accusation.setRoomCard(rooms.get(ThreadLocalRandom.current().nextInt(rooms.size())));
        game.setCrimeScene(accusation);
        List<Card> crimeSceneCards = List.of(accusation.getRoomCard(),accusation.getSuspectCard(),accusation.getWeaponCard());
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
            player.addCards(card);
            players.set(i,player);
            remainingCards.remove(card);
            i++;
        }
        for (UserGame player:players){
            userGameService.saveUserGame(player);
        }
    }
}
