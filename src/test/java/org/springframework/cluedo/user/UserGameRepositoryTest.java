package org.springframework.cluedo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserGameRepositoryTest {
    
    @Autowired
    protected UserGameRepository repo;
    User user;
    Game game;
    UserGame userGame;
    @BeforeEach
    public void config(){

        user = new User();
        user.setId(1);
        
        game = new Game();
        game.setId(1);

        userGame = new UserGame();
        userGame.setId(1);
        userGame.setOrderUser(0);
        userGame.setAccusationsNumber(1);
        userGame.setIsAfk(true);
        userGame.setIsEliminated(true);
        userGame.setSuspect(SuspectType.BLUE);
        userGame.setUser(user);
        userGame.setGame(game);
    }

    @Test
    public void testFindPlayerByGameAndOrder() {
        Optional<UserGame> userGame = repo.findPlayerByGameAndOrder(game, 0);
        assertTrue(userGame.isPresent());
        userGame = repo.findPlayerByGameAndOrder(null, null);
        assertFalse(userGame.isPresent());
    }

    @Test
    public void testRemainingPlayersNotEliminated() {
        List<UserGame> usersRemaining = repo.remainingPlayersNotEliminated(game);
        assertNotNull(usersRemaining);
        assertTrue(usersRemaining.size() >=2 && usersRemaining.size() <= 6);
        assertFalse(usersRemaining.isEmpty());
    }

    @Test
    public void testGetUserGameByUserId() {
        UserGame userGame = repo.getUserGameByUserId(user.getId());
        assertNotNull(userGame);
    }
}
