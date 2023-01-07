package org.springframework.cluedo.card;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.celd.CeldService;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
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
