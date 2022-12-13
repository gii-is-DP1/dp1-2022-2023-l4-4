package org.springframework.cluedo.turn;

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

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TurnServiceTest {
    
    @MockBean
    private TurnRepository turnRepository;

    @MockBean
    private CeldService celdService;

    @Autowired
    protected TurnService turnService;

    private Turn turn1;
    private Game game1;
    private UserGame ug1;
    private Celd celd1;
    private Celd celd2;

    @BeforeEach
    public void init() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setId(100);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("password");
        user2.setId(200);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("user3@gmail.com");
        user3.setPassword("password");
        user3.setId(300);

        game1 = new Game();
        game1.setHost(user1);
        game1.setId(100);
        game1.setIsPrivate(false);
        game1.setStatus(Status.LOBBY);
        game1.setRound(1);

        List<User> lobby1 = new ArrayList<>();
        lobby1.add(user1);
        lobby1.add(user2);
        lobby1.add(user3);
        game1.setLobby(lobby1);
        game1.setLobbySize(6);

        ug1 = new UserGame();
        ug1.setGame(game1);
        ug1.setOrderUser(1);
        ug1.setSuspect(SuspectType.BLUE);
        ug1.setIsAfk(false);
        ug1.setIsEliminated(false);
        ug1.setId(100);
        ug1.setUser(user1);

        game1.setActualPlayer(ug1);
        game1.setPlayers(List.of(ug1));

        turn1 = new Turn();
        turn1.setId(100);
        turn1.setUserGame(ug1);
        turn1.setPhase(Phase.DICE);
        celd1 = new Celd();
        celd1.setId(100);
        celd1.setPosition(1);

        celd2 = new Celd();
        celd2.setId(200);
        celd2.setPosition(2);

        turn1.setInitialCeld(celd1);
        turn1.setFinalCeld(celd1);
    }

    @Test
    public void shouldCreateFirstTurn() {

        Optional<Turn> emptyTurn = Optional.empty();
        when(turnRepository.getTurn(anyInt(),anyInt())).thenReturn(emptyTurn);
        when(celdService.getCenter()).thenReturn(celd1);
        when(turnRepository.save(turn1)).thenReturn(null);
        
        Turn newTurn = turnService.createTurn(ug1, game1.getRound());

        assertEquals(ug1, newTurn.getUserGame());
        assertEquals(game1.getRound(), newTurn.getRound());
        assertEquals(Phase.DICE, newTurn.getPhase());
        assertEquals(celd1, newTurn.getInitialCeld());

    }

    @Test
    public void shouldCreateOtherTurn() {

        Optional<Turn> firstTurn = Optional.of(turn1);
        when(turnRepository.getTurn(anyInt(),anyInt())).thenReturn(firstTurn);
        when(turnRepository.save(turn1)).thenReturn(null);
        
        Turn newTurn = turnService.createTurn(ug1, 2);

        assertEquals(ug1, newTurn.getUserGame());
        assertEquals(2, newTurn.getRound());
        assertEquals(Phase.DICE, newTurn.getPhase());
        assertEquals(firstTurn.get().getFinalCeld(), newTurn.getInitialCeld());

    }

    @Test
    public void shouldThrowDices() {
        Optional<Turn> optTurn = Optional.of(turn1);
        when(turnRepository.getTurn(anyInt(), anyInt())).thenReturn(optTurn);
        when(turnRepository.save(any(Turn.class))).thenReturn(turn1);
        boolean throwsException = false;
        Turn diceThrownTurn = null;
        try {
            diceThrownTurn = turnService.throwDice(game1);
        } catch(Exception e) {
            throwsException = true;
        }

        assertEquals(false, throwsException);
        assertTrue(2<=diceThrownTurn.getDiceResult() && 12>=diceThrownTurn.getDiceResult());
        assertEquals(Phase.MOVEMENT, diceThrownTurn.getPhase());
        
    }

    @Test
    public void shouldNotThrowDicesBecausePhase() {
        turn1.setPhase(Phase.ACCUSATION);
        Optional<Turn> optTurn = Optional.of(turn1);
        when(turnRepository.getTurn(anyInt(), anyInt())).thenReturn(optTurn);
        boolean throwsException = false;
        try {
            turnService.throwDice(game1);
        } catch(Exception e) {
            throwsException = true;
        }

        assertEquals(true, throwsException);
        
    }

    @Test
    public void shouldNotThrowDicesBecauseNoTurn() {
        Optional<Turn> optTurn = Optional.empty();
        when(turnRepository.getTurn(anyInt(), anyInt())).thenReturn(optTurn);
        boolean throwsException = false;
        try {
            turnService.throwDice(game1);
        } catch(Exception e) {
            throwsException = true;
        }

        assertEquals(true, throwsException);
        
    }

}
