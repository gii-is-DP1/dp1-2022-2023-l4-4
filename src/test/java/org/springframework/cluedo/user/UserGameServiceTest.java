package org.springframework.cluedo.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.cluedo.card.Card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.turn.Turn;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserGameServiceTest {
    
    @Mock
    protected UserGameRepository userGameRepo;

    protected UserGameService userGameService;

    @Mock
    protected AccusationService accusationService;

    User user1;
    User user2;
    Game game;
    UserGame userGame1;
    UserGame userGame2;

    @BeforeEach
    public void config(){

        user1 = new User();
        user1.setId(1);
        
        user2= new User();
        user2.setId(2);

        game = new Game();
        game.setId(1);
        

        userGame1 = new UserGame();
        userGame1.setId(1);
        userGame1.setOrderUser(1);
        userGame1.setAccusationsNumber(1);
        userGame1.setIsAfk(true);
        userGame1.setIsEliminated(true);
        userGame1.setSuspect(SuspectType.BLUE);
        userGame1.setUser(user1);
        userGame1.setGame(game);

        userGame2 = new UserGame();
        userGame2.setId(2);
        userGame2.setOrderUser(2);
        userGame2.setAccusationsNumber(2);
        userGame2.setIsAfk(false);
        userGame2.setIsEliminated(false);
        userGame2.setSuspect(SuspectType.GREEN);
        userGame2.setUser(user2);
        userGame2.setGame(game);

        game.setPlayers(new ArrayList<>(List.of(userGame1, userGame2)));
        game.setActualPlayer(userGame1);
        userGameService = new UserGameService(userGameRepo, accusationService);
    }

    @Test
    public void testSaveUserGame() {
        UserGame userGameToSave = new UserGame();
        userGameToSave.setId(2);
        userGameToSave.setOrderUser(1);
        userGameToSave.setAccusationsNumber(2);
        userGameToSave.setIsAfk(true);
        userGameToSave.setIsEliminated(false);
        userGameToSave.setSuspect(SuspectType.GREEN);
        userGameToSave.setUser(user2);
        userGameToSave.setGame(game);

        userGameService.saveUserGame(userGameToSave);
        verify(userGameRepo,times(1)).save(userGameToSave);        
    }

    @Test
    public void testGetUsergameByGameAndOrder() {
        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class),any(Integer.class))).thenReturn(Optional.of(userGame1));
    
        UserGame userGame = userGameService.getUsergameByGameAndOrder(game, 0);
        assertNotNull(userGame);
        assertTrue(userGame.getGame().getId()==1 && userGame.getOrderUser()==1);
    }
    
    @Test
    public void testGetFirstUsergame() {
        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class), anyInt())).thenReturn(Optional.of(userGame1));

        UserGame userGame = userGameService.getFirstUsergame(game);
        assertNotNull(userGame);
        assertTrue(userGame.getGame().getId()==1 && userGame.getOrderUser()==1);
    }

    @Test
    public void testGetLastUserGame(){
        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class),any(Integer.class))).thenReturn(Optional.of(userGame2));
        
        UserGame userGame = userGameService.getLastUsergame(game);
        assertNotNull(userGame);
        assertTrue(userGame.getOrderUser()==2 && userGame.getUser().getId()==2);
    }

    @Test
    public void testGetNextUsergame() {
        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class), any(Integer.class))).thenReturn(Optional.of(userGame2));

        Optional<UserGame> userGame = userGameService.getNextUsergame(game);
        assertNotNull(userGame);
        assertTrue(userGame.get().getOrderUser()==game.getActualPlayer().getOrderUser()+1);
    }

    @Test
    public void testRemainingPlayersNotEliminated(){
        when(userGameRepo.remainingPlayersNotEliminated(any(Game.class))).thenReturn(List.of(userGame2));

        List<UserGame> remainingPlayers = userGameService.remainingPlayersNotEliminated(game);
        assertNotNull(remainingPlayers);
        assertTrue(remainingPlayers.stream().allMatch(userGame -> !userGame.getIsEliminated()));
    }

    @Test
    public void testWhoShouldGiveCardFails(){
        Turn turn = new Turn();
        turn.setId(1);
        turn.setUserGame(userGame1);

        Accusation accusation = new Accusation();
        accusation.setId(1);
        accusation.setTurn(turn);

        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class),eq(1))).thenReturn(Optional.of(userGame1));
        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class),eq(2))).thenReturn(Optional.of(userGame2));
        when(accusationService.getMatchingCardsFromUser(any(Accusation.class), any(UserGame.class))).thenReturn(new ArrayList<>());

        UserGame userGame = userGameService.whoShouldGiveCard(game, accusation);
        assertNull(userGame);
    }
    
    @Test
    public void testShouldNotGiveCard() {
        Turn turn = new Turn();
        turn.setId(1);
        turn.setUserGame(userGame1);

        Accusation accusation = new Accusation();
        accusation.setId(1);
        accusation.setTurn(turn);

        when(userGameRepo.findPlayerByGameAndOrder(any(Game.class),eq(2))).thenReturn(Optional.of(userGame2));
        when(accusationService.getMatchingCardsFromUser(any(Accusation.class), any(UserGame.class))).thenReturn(List.of(new Card()));

        UserGame userGame = userGameService.whoShouldGiveCard(game, accusation);
        assertNotNull(userGame);
    }

    
}