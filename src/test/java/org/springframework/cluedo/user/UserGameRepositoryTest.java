package org.springframework.cluedo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @BeforeEach
    public void config(){

        user = new User();
        user.setId(1);
        
        game = new Game();
        game.setId(1);
    }

    @Test
    public void testFindPlayerByGameAndOrder() {
        Optional<UserGame> userGame = repo.findPlayerByGameAndOrder(game, 1);
        assertTrue(userGame.isPresent());
        userGame = repo.findPlayerByGameAndOrder(game, 10);
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
    public void testFindPlayerByGame() {
        List<UserGame> players = repo.findPlayerByGame(game);
        assertNotNull(players);
        assertTrue(!players.isEmpty());
    }
    
    @Test
    public void testSetNullUser(){
        repo.setNullUser(1);
        List<UserGame> userGames = repo.findUserGameByGameId(1);
        assertTrue(userGames.isEmpty());
    }

    @Test
    public void testFindUsergameByGameAndUser(){
        UserGame userGame = repo.findUsergameByGameAndUser(game, user);
        assertNotNull(userGame);
    }

    @Test
    public void testFindUserGameByGameId(){
        List<UserGame> userGames = repo.findUserGameByGameId(game.getId());
        assertFalse(userGames.isEmpty());
    }

    }

