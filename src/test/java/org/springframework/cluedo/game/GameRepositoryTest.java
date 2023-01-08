package org.springframework.cluedo.game;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    protected GameRepository repo;

    Game game;
    User user;

    @BeforeEach
    public void config() {
        user = new User();
        user.setId(1);
        
        game = new Game();
        game.setId(1);
        game.setStatus(Status.LOBBY);
        game.setLobby(new ArrayList<>());
        game.addLobbyUser(user);
        
    }

    @Test
    public void testfindAll() {
        List<Game> games = repo.findAll();
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    public void testFindAllPublicLobbies() {
        List<Game> games = repo.findAllPublicLobbies();
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    public void testFindAllNotFinishedGames() {
        List<Game> games = repo.findAllNotFinishedGames();
        assertNotNull(games);
        assertFalse(games.stream().allMatch(x -> x.getStatus().equals(Status.FINISHED)));
    }

    @Test
    public void testFindAllFinishedGames() {
        List<Game> games = repo.findAllFinishedGames();
        assertNotNull(games);
        assertFalse(games.stream().allMatch(x -> x.getStatus()!=Status.FINISHED));
    }

    @Test
    public void getMyNotFinishedGame() {
        Game myGame = repo.getMyNotFinishedGame(user);
        assertNotNull(myGame);
        assertTrue(myGame.getStatus()!=Status.FINISHED);
    }
}