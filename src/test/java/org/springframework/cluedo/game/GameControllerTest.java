package org.springframework.cluedo.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers=GameController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class GameControllerTest {
    
    @MockBean
    protected GameService gameService; 

    @MockBean
    protected TurnService turnService; 

    @MockBean
    protected UserService userService;

    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    //private GameController gameController;

    private User user1=new User();
    private User user2=new User();
    private User user3=new User();
    private Game ipGame=new Game();
    private Game lobbyGame=new Game();
    private Game fGame=new Game();
    private Game newGame=new Game(); 
    @BeforeEach
    public void config(){
        
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

        
        ipGame.setId(1);
        ipGame.setHost(user1);
        ipGame.setLobbySize(5);
        ipGame.setLobby(List.of(user1,user2,user3));
        ipGame.setStatus(Status.IN_PROGRESS);

        lobbyGame.setId(2);
        lobbyGame.setHost(user2);
        lobbyGame.setLobbySize(3);
        lobbyGame.setLobby(List.of(user2,user3));
        lobbyGame.setStatus(Status.LOBBY);
        
        fGame.setId(3);
        fGame.setHost(user3);
        fGame.setLobbySize(5);
        fGame.setLobby(List.of(user1,user2,user3));
        fGame.setStatus(Status.FINISHED);

        newGame.setId(4);
        newGame.setHost(user3);
        newGame.setLobbySize(5);
        newGame.setIsPrivate(false);

        UserGame ug1=new UserGame();
        ug1.setId(5);
        ug1.setUser(user1);
        ug1.setGame(ipGame);

        
        
       
        when (turnService.createTurn(any(UserGame.class), any(Integer.class))).thenReturn(new Turn());
        when(gameService.getAllNotFinishedGames()).thenReturn(new ArrayList<>());
    }

    @WithMockUser
    @Test
    public void testGetAllActiveGames() throws Exception{
        mockMvc.perform(get("/games/admin/active")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gameList")).
                andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastGames() throws Exception{
        mockMvc.perform(get("/games/admin/past")).
                andExpect(status().isOk()).
                andExpect(view().name("games/gamePastList")).
                andExpect(model().attributeExists("games")).
                andExpect(model().attributeExists("admin"));
    }

    @WithMockUser
    @Test
    public void testGetAllPublicLobbies() throws Exception{

        when(userService.getLoggedUser()).thenReturn(Optional.of(new User()));
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
        mockMvc.perform(get("/games")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games/"+ipGame.getId()+"/play"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastUserGames() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
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
    //@Test
    public void testPostFormCreateGame() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when (gameService.getMyNotFinishedGame(any(User.class))).thenReturn(null);
        mockMvc.perform(post("/games/new",newGame,new MapBindingResult(new HashMap<>(),"Game"))).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:"+newGame.getId()+"/lobby"));
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
        
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/games"));

        when(userService.getLoggedUser()).thenReturn(Optional.of(user3));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobbyPlayer")).
                andExpect(model().attributeExists("lobby"));
    }

    @WithMockUser
    @Test
    public void testLeaveGame() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user2));
        when (gameService.getGameById(any(Integer.class))).thenReturn(lobbyGame);
        mockMvc.perform(get("/games/"+lobbyGame.getId()+"/lobby")).
                andExpect(status().isOk()).
                andExpect(view().name("games/lobby")).
                andExpect(model().attributeExists("lobby"));
        
    }

}
