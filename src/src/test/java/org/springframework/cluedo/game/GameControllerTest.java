package org.springframework.cluedo.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.enumerates.CardName;
import org.springframework.cluedo.enumerates.CardType;
import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.message.MessageService;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = GameController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTest {

        @MockBean
        protected GameService gameService;

        @MockBean
        protected TurnService turnService;

        @MockBean
        protected UserService userService;

        @MockBean
        private CardService cardService;

        @MockBean
        private AccusationService accusationService;

        @MockBean
        private UserGameService userGameService;

        @MockBean
        private MessageService messageService;

        @Autowired
        private MockMvc mockMvc;


        private User user1;
        private User user2;
        private User user3;
        private User user4;
        private Game ipGame;
        private Game lobbyGame;
        private Game fGame;
        private Game newGame;
        private Turn turn;
        private Celd celd;
        private Card roomCard;
        private Card suspectCard;
        private Card weaponCard;
        private Accusation accusation;
        private UserGame ug1;
        private UserGame ug2;
        private UserGame ug3;

        @BeforeEach
        public void config() {
                user1 = new User();
                user2 = new User();
                user3 = new User();
                user4 = new User();
                ipGame = new Game();
                lobbyGame = new Game();
                fGame = new Game();
                newGame = new Game();

                user1.setId(1);
                user1.setEmail("a@a.a");
                user1.setUsername("Paco");
                user1.setPassword("Paco");
                user1.setAuthority("admin");
                user1.setEnabled(1);

                user2.setId(2);
                user2.setEmail("b@a.a");
                user2.setUsername("Manu");
                user2.setPassword("Manu");
                user2.setAuthority("user");
                user2.setEnabled(1);

                user2.setId(3);
                user2.setEmail("c@a.a");
                user2.setUsername("Luis");
                user2.setPassword("Luis");
                user2.setAuthority("user");
                user2.setEnabled(1);

                List<User> list = new ArrayList<>();
                list.add(user1);
                list.add(user2);
                list.add(user3);

                ipGame.setId(1);
                ipGame.setHost(user1);
                ipGame.setLobbySize(5);
                ipGame.setLobby(list);
                ipGame.setStatus(Status.IN_PROGRESS);

                lobbyGame.setId(2);
                lobbyGame.setHost(user2);
                lobbyGame.setLobbySize(3);
                lobbyGame.setLobby(list);
                lobbyGame.setStatus(Status.LOBBY);

                fGame.setId(3);
                fGame.setHost(user3);
                fGame.setLobbySize(5);
                fGame.setLobby(list);
                fGame.setStatus(Status.FINISHED);

                newGame.setId(4);
                newGame.setHost(user3);
                newGame.setLobbySize(5);
                newGame.setIsPrivate(false);

                ug1 = new UserGame();
                ug1.setId(1);
                ug1.setUser(user1);
                ug1.setGame(ipGame);
                ug2 = new UserGame();
                ug2.setId(2);
                ug2.setUser(user2);
                ug2.setGame(ipGame);
                
                ug2.setIsEliminated(false);
                ug3 = new UserGame();
                ug3.setId(3);
                ug3.setUser(user3);
                ug3.setGame(ipGame);

                ipGame.setPlayers(List.of(ug1, ug2, ug3));
                ipGame.setRound(1);
                ipGame.setActualPlayer(ug1);

                turn = new Turn();
                turn.setId(1);
                turn.setPhase(Phase.DICE);
                turn.setRound(1);
                turn.setUserGame(ug1);
                turn.setFinalCeld(celd);

                celd = new Celd();
                celd.setId(1);
                celd.setPosition(78);
                celd.setCeldType(CeldType.CORRIDOR);

                roomCard = new Card();
                roomCard.setId(1);
                roomCard.setCardName(CardName.DINNINGHALL);
                roomCard.setCardType(CardType.ROOM);

                weaponCard = new Card();
                weaponCard.setId(2);
                weaponCard.setCardName(CardName.GUN);
                weaponCard.setCardType(CardType.WEAPON);

                suspectCard = new Card();
                suspectCard.setId(3);
                suspectCard.setCardName(CardName.BLUE);
                suspectCard.setCardType(CardType.SUSPECT);

                accusation = new Accusation();
                accusation.setId(1);
                accusation.setRoomCard(roomCard);
                accusation.setTurn(turn);
                accusation.setWeaponCard(weaponCard);
                accusation.setSuspectCard(suspectCard);

                when(turnService.createTurn(any(UserGame.class), any(Integer.class))).thenReturn(new Turn());
        }

    @WithMockUser
    @Test
    public void testGetAllActiveGames() throws Exception{
        
        when(gameService.getAllNotFinishedGames()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/games/admin/active")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastGames() throws Exception{
        
        when(gameService.getAllFinishedGames()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/games/admin/past")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gamePastList")).
                andExpect(model().attributeExists("games")).
                andExpect(model().attributeExists("admin"));
    }

    @WithMockUser
    @Test
    public void testGetAllPublicLobbies() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(null);
        mockMvc.perform(get("/games")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attributeExists("games"));
                when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
                when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(lobbyGame);
        
        mockMvc.perform(get("/games")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+lobbyGame.getId()+"/lobby"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(ipGame);
        when(userGameService.getUsergameByGameAndUser(any(), any())).thenReturn(ug2);
        mockMvc.perform(get("/games")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastUserGames() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getAllFinishedGames()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/games/admin/past")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gamePastList")).
                andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testInitFormCreateGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(null);
        mockMvc.perform(get("/games/new")).
                andExpect(status().isOk()).
                andExpect(view().name("games/createNewGame")).
                andExpect(model().attributeExists("privateList","nPlayers","game","user"));
    }

    @WithMockUser
    @Test
    public void testPostFormCreateGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(null);
        mockMvc.perform(post("/games/new").with(csrf())
        .param("id",newGame.getId().toString())
        .param("status","LOBBY")
        .param("host",user1.getId().toString())
        .param("isPrivate","false")
        .param("lobbySize","6")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+newGame.getId()+"/lobby"));
    }

    @WithMockUser
    @Test
    public void testGetLobby() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attributeExists("lobby"));
        
        when(userService.getLoggedUser()).thenReturn(Optional.of(user4));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user3));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attributeExists("lobby"));
    }

    @WithMockUser
    @Test
    public void testLeaveGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attributeExists("lobby"));
    }

    @WithMockUser
    @Test
    public void testJoinGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        mockMvc.perform(get("/games/"+ipGame.getId())).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attribute("message","The game is started"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user4));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId())).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attribute("message","The lobby is full"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(model().attribute("lobby",lobbyGame));

        lobbyGame.setLobbySize(4);
        when(userService.getLoggedUser()).thenReturn(Optional.of(user4));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(model().attributeExists("lobby"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getGameById(any(Integer.class))).thenThrow(new DataNotFound());
        mockMvc.perform(get("/games/100")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attribute("message","The game doesn't exist"));
    }

    @WithMockUser
    @Test
    public void testStartGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(post("/games/"+lobbyGame.getId()+"/lobby").with(csrf())).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attribute("message","The host is incorrect"));

        
        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(post("/games/"+lobbyGame.getId()+"/lobby").with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+lobbyGame.getId()+"/play"));
        
        lobbyGame.removeLobbyUser(user1);
        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(post("/games/"+lobbyGame.getId()+"/lobby").with(csrf())).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attribute("message","The game needs at least 3 players to start"));
    }

    @WithMockUser
    @Test
    public void testPlayGame() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when(turnService.getActualTurn(any())).thenReturn(Optional.of(turn));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(false);
        when(userGameService.getUsergameByGameAndUser(any(),any())).thenReturn(ug2);
        ug2.setCards(Set.of(roomCard,suspectCard,weaponCard));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().isOk()).
                andExpect(view().name("games/onGame")).
                andExpect(model().attributeExists("game"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play/dice"));

        turn.setPhase(Phase.MOVEMENT);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play/move"));
                
        turn.setPhase(Phase.ACCUSATION);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play/accusation"));

        turn.setPhase(Phase.FINAL);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play/finish"));

        turn.setPhase(Phase.FINISHED);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play")).
                andExpect(status().isOk()).
                andExpect(view().name("games/onGame")).
                andExpect(model().attributeExists("game"));
    }

    @WithMockUser
    @Test
    public void testThrowDicesButton() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play/dice")).
                andExpect(status().isOk()).
                andExpect(view().name("games/diceView")).
                andExpect(model().attributeExists("gameId"));

    }

    @WithMockUser
    @Test
    public void testThrowDices() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(post("/games/"+ipGame.getId()+"/play/dice").with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play"));

    }

    @WithMockUser
    @Test
    public void testMovementPosibilities() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        turn.setDiceResult(8);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        when(userGameService.getUsergameByGameAndUser(any(),any())).thenReturn(ug2);
        ug2.setCards(Set.of(roomCard,suspectCard,weaponCard));
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play/move")).
                andExpect(status().isOk()).
                andExpect(view().name("games/selectCeld")).
                andExpect(model().attributeExists("diceResult")).
                andExpect(model().attributeExists("celdForm")).
                andExpect(model().attributeExists("movements"));

    }

    @WithMockUser
    @Test
    public void testMoveTo() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        when(turnService.getActualTurn(any())).thenReturn(Optional.of(turn));
        turn.setPhase(Phase.MOVEMENT);
        turn.setFinalCeld(celd);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(post("/games/"+ipGame.getId()+"/play/move").with(csrf())
                .param("finalCeld.id","1")
                .param("finalCeld.position","78")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play"));

    }

    @WithMockUser
    @Test
    public void testMakeAccusationButton() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        turn.setPhase(Phase.ACCUSATION);
        turn.setFinalCeld(celd);
        celd.setCeldType(CeldType.DINNINGHALL);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        when( cardService.getCardByCardName(any())).thenReturn(roomCard);
        mockMvc.perform(get("/games/"+ipGame.getId()+"/play/accusation")).
                andExpect(status().isOk()).
                andExpect(view().name("games/makeAccusation")).
                andExpect(model().attributeExists("suspects")).
                andExpect(model().attributeExists("weapons")).
                andExpect(model().attributeExists("room")).
                andExpect(model().attributeExists("accusation"));
    }

    @WithMockUser
    @Test
    public void testFinishTurn() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(gameService.getGameById(any(Integer.class))).thenReturn(ipGame);
        when(gameService.isGameInProgress(any(Game.class))).thenReturn(true);
        when(gameService.isUserTurn(any(), any(Game.class))).thenReturn(true);
        turn.setPhase(Phase.FINAL);
        when(turnService.getActualTurn(any(Game.class))).thenReturn(Optional.of(turn));
        mockMvc.perform(post("/games/"+ipGame.getId()+"/play/finish").with(csrf()).param("gameId", ipGame.getId().toString())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play"));
    }
}
