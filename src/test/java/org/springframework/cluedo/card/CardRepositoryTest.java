package org.springframework.cluedo.card;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardRepositoryTest {

    @Autowired
    protected CardRepository repo;

    @Test
    public void findAllTest(){
        List<Card> list = repo.findAll();
        Integer totalNumCards = 21;
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.size()==totalNumCards);
    }

    @Test
    public void findWeaponsTest(){
        List<Card> list = repo.findWeapons();
        Integer totalNumWeaponsCards =6;
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.size()==totalNumWeaponsCards);
        assertTrue(list.get(0).getCardType()==CardType.WEAPON);
    }

    @Test
    public void findSuspectsTest(){
        List<Card> list = repo.findSuspects();
        Integer totalNumSuspectsCards =6;
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.size()==totalNumSuspectsCards);
        assertTrue(list.get(0).getCardType()==CardType.SUSPECT);
    }

    @Test
    public void findRoomsTest(){
        List<Card> list = repo.findRooms();
        Integer totalNumRoomsCards =9;
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.size()==totalNumRoomsCards);
        assertTrue(list.get(0).getCardType()==CardType.ROOM);
    }

    
}
