package org.springframework.cluedo.game;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {

    @Autowired
    protected GameService gameService;
    @Autowired
    protected UserService userService;

    @Test
    void shouldFindNotFinishedGames() {
        List<Game> games = gameService.getAllNotFinishedGames();
        Game game2 = games.stream().filter(g->g.getId()==2).findFirst().orElse(null);
        Game game3 = games.stream().filter(g->g.getId()==3).findFirst().orElse(null);
        assertEquals(2,games.size());
        assertNotNull(game2);
        assertNotNull(game3);
        assertEquals(Status.IN_PROGRESS, game2.getStatus());
        assertEquals(Status.LOBBY, game3.getStatus());
    }

    @Test
    void shouldFindFinishedGames() {
        List<Game> games = gameService.getAllFinishedGames();
        Game game1 = games.stream().filter(g->g.getId()==1).findFirst().orElse(null);
        assertEquals(1,games.size());
        assertNotNull(game1);
        assertEquals(Status.FINISHED, game1.getStatus());
    }

    @Test
    void shouldFindPublicLobbies() {
        List<Game> games = gameService.getAllPublicLobbies();
        Game game = games.stream().filter(g->g.getId()==3).findFirst().orElse(null);
        assertEquals(1,games.size());
        assertNotNull(game);
        assertEquals(Status.LOBBY, game.getStatus());
    }
}
