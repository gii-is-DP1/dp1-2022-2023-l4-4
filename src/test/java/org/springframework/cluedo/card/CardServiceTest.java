package org.springframework.cluedo.card;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardServiceTest {
    
    @Autowired
    protected CardService service;

    @Test
    public void getCardsTest(){
        List<Card> cards = service.getAllCards();
        Integer numCards = 21;
        assertFalse(cards.isEmpty());
        assertTrue(cards.size()==numCards);
        
    }


}
