package org.springframework.cluedo.accusation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.user.UserGame;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AccusationServiceTest {
    
    protected AccusationService accusationService;

    @Mock
    protected AccusationRepository accusationRepository;

    @Mock
    protected CrimeSceneRepository crimeSceneRepository;

    @Mock
    protected FinalAccusationRepository finalAccusationRepository;

    Accusation accusation;

    Card roomCard;
    
    Card suspectCard;
    
    Card weaponCard;
    
    Card otherCard;
    
    UserGame userGame;
    
    Turn turn;

    CrimeScene crimeScene;

    FinalAccusation finalAccusation;

    @BeforeEach
    public void config(){

        accusationService = new AccusationService(accusationRepository, crimeSceneRepository, finalAccusationRepository);

        roomCard=new Card();
        roomCard.setId(1);

        
        suspectCard=new Card();
        roomCard.setId(2);
        
        weaponCard=new Card();
        roomCard.setId(3);
        
        otherCard = new Card();
        otherCard.setId(4);

        accusation=new Accusation();
        accusation.setId(1);
        accusation.setRoomCard(roomCard);
        accusation.setSuspectCard(suspectCard);
        accusation.setWeaponCard(weaponCard);
        
        Game g = new Game();
        g.setId(1);

        userGame=new UserGame();
        userGame.setId(1);
        userGame.setCards(Set.of(roomCard,suspectCard,weaponCard));
        userGame.setGame(g);

        turn=new Turn();
        turn.setId(1);
        turn.setUserGame(userGame);

        crimeScene=new CrimeScene();
        crimeScene.setId(1);
        crimeScene.setWeaponCard(weaponCard);
        crimeScene.setSuspectCard(suspectCard);
        crimeScene.setRoomCard(roomCard);

        finalAccusation = new FinalAccusation();
        finalAccusation.setId(1);
        finalAccusation.setTurn(turn);
        finalAccusation.setRoomCard(roomCard);
        finalAccusation.setSuspectCard(suspectCard);
        finalAccusation.setWeaponCard(weaponCard);

        

    }

    @Test
    public void getMatchingCardsFromUserTest(){
        
        List<Card> res = accusationService.getMatchingCardsFromUser(accusation, userGame);
        assertTrue(res.size()==3);

        userGame.setCards(Set.of(roomCard,otherCard));
        res = accusationService.getMatchingCardsFromUser(accusation, userGame);
        assertTrue(res.size()==1);

        userGame.setCards(Set.of(otherCard));
        res=accusationService.getMatchingCardsFromUser(accusation, userGame);
        assertTrue(res.size()==0);

    }

    @Test
    public void isFinalAccusationCorrectTest(){

        when(crimeSceneRepository.findByGame(anyInt())).thenReturn(crimeScene);
        when(finalAccusationRepository.findByTurn(anyInt())).thenReturn(finalAccusation);
        boolean res = accusationService.isFinalAccusationCorrect(turn);
        assertTrue(res);
 
        finalAccusation.setSuspectCard(otherCard);
        res = accusationService.isFinalAccusationCorrect(turn);
        assertFalse(res);

    }

    @Test
    public void showCardTest(){

        when(accusationRepository.findByTurn(turn.getId())).thenReturn(Optional.of(accusation));
        accusationService.showCard(turn,weaponCard);
        accusation.setShownCard(weaponCard);
        verify(accusationRepository,times(1)).save(accusation);

    }

}
