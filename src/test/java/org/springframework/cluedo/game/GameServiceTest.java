package org.springframework.cluedo.game;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
    
    @Mock
	private GameRepository gameRepository;

    @Mock
	private UserService userService;

    @Mock
	private CardService cardService;

    private List<Game> mockGames;

    
    protected GameService gameService;

    @BeforeEach
    public void initMock() {
        mockGames = new ArrayList<Game>();

        //Initialize users
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

        //Initialize Games
        Game game1 = new Game();
        game1.setHost(user1);
        game1.setId(100);
        game1.setIsPrivate(false);
        game1.setStatus(Status.LOBBY);
        List<User> lobby1 = new ArrayList<>();
        lobby1.add(user1);
        lobby1.add(user2);
        lobby1.add(user3);
        game1.setLobby(lobby1);
        game1.setLobbySize(6);

        Game game2 = new Game();
        game2.setHost(user2);
        game2.setId(100);
        game2.setIsPrivate(true);
        game1.setStatus(Status.LOBBY);
        List<User> lobby2 = new ArrayList<>();
        lobby2.add(user1);
        lobby2.add(user2);
        lobby2.add(user3);
        game2.setLobby(lobby2);
        game2.setLobbySize(3);

        Game game3 = new Game();
        game3.setHost(user3);
        game3.setId(100);
        game3.setIsPrivate(false);
        game1.setStatus(Status.LOBBY);
        List<User> lobby3 = new ArrayList<>();
        lobby3.add(user1);
        lobby3.add(user2);
        lobby3.add(user3);
        game3.setLobby(lobby3);
        game3.setLobbySize(6);

        mockGames.add(game1);
        mockGames.add(game2);
        mockGames.add(game3);

        gameService = new GameService(gameRepository, null, cardService, userService, null,null, null);
    }

    @Test
    void shouldInitializeGame() {
        Game game1 = mockGames.get(0);
        UserGame ug = new UserGame();
        game1.setPlayers(List.of(ug));
        gameService.initGame(game1);
        assertEquals(Status.IN_PROGRESS, game1.getStatus());
        assertEquals(Duration.ofMinutes(0), game1.getDuration());
        assertEquals(ug, game1.getActualPlayer());
    }

    @Test
    void shouldReturnTrueIsUserTurn() {
        Game game1 = mockGames.get(0);
        UserGame ug = new UserGame();
        User u = game1.getLobby().get(0);
        ug.setUser(u);
        game1.setActualPlayer(ug);
        Optional<User> user = Optional.of(u);
        boolean res = gameService.isUserTurn(user, game1);
        assertEquals(true, res);
    }

    @Test
    void shouldReturnFalseIsUserTurn() {
        Game game1 = mockGames.get(0);
        UserGame ug = new UserGame();
        User u = game1.getLobby().get(0);
        ug.setUser(u);
        game1.setActualPlayer(ug);
        Optional<User> user = Optional.of(game1.getLobby().get(1));
        boolean res = gameService.isUserTurn(user, game1);
        assertEquals(false, res);
    }

    @Test
    void shouldDeleteUserFromLobby() {
        Game game1 = mockGames.get(0);
        User user = game1.getLobby().get(0);
        assertEquals(true, game1.getLobby().contains(user));
        when(gameRepository.save(game1)).thenReturn(game1);
        gameService.deleteUserFromLobby(user, game1);
        assertEquals(false, game1.getLobby().contains(user));
    }
}
