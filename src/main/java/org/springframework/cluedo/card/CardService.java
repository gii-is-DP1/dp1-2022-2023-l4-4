package org.springframework.cluedo.card;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public void initCards(Game game) {
        List<Card> weapons = cardRepository.findWeapons();
        List<Card> suspects = cardRepository.findSuspects();
        List<Card> rooms = cardRepository.findRooms();
        Accusation accusation = new Accusation();
        accusation.setWeaponCard(weapons.get(ThreadLocalRandom.current().nextInt(weapons.size())));
        game.setCrimeScene(null);
        List<UserGame> players = game.getPlayers();
    }
}
