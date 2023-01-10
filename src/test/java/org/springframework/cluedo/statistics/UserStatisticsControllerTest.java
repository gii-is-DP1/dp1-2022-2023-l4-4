package org.springframework.cluedo.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameService;
import org.springframework.cluedo.user.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.context.annotation.FilterType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers=UserStatisticsControllerTest.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class UserStatisticsControllerTest {

    @MockBean
    protected UserStatisticsService userStatisticsService;

    @MockBean
    protected GameService gameService;

    @Autowired
    protected MockMvc mockMvc;

    private UserStatistics stats;
    private List<UserStatistics> lStatistics;
    private List<Game> lGames;
    @BeforeEach
    public void config(){

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
        game1.setDuration(Duration.ofSeconds(5000));
        game1.setIsPrivate(false);
        game1.setStatus(Status.FINISHED);
        List<User> lobby1 = new ArrayList<>();
        lobby1.add(user1);
        lobby1.add(user2);
        lobby1.add(user3);
        game1.setLobby(lobby1);
        game1.setLobbySize(6);

        Game game2 = new Game();
        game2.setHost(user2);
        game2.setId(100);
        game2.setDuration(Duration.ofSeconds(1000));
        game2.setIsPrivate(true);
        game2.setStatus(Status.FINISHED);
        List<User> lobby2 = new ArrayList<>();
        lobby2.add(user1);
        lobby2.add(user2);
        lobby2.add(user3);
        game2.setLobby(lobby2);
        game2.setLobbySize(3);

        stats= new UserStatistics();
        stats.setId(1); 
        stats.setAfkCounter(11); 
        stats.setLongestGame(game1); 
        stats.setShortestGame(game2); 
        stats.setTotalAccusations(2);
        stats.setTotalFinalAccusations(1);
        stats.setTotalGames(1); 
        stats.setTotalRounds(5); 
        stats.setTotalTime(6000); 
        stats.setUser(user1); 
        stats.setVictories(1); 
        stats.setXp(10); 

        lStatistics=new ArrayList<>();
        lGames=new ArrayList<>();
        lStatistics.add(stats);
        lGames.add(game2);
        lGames.add(game1);

    }

    @WithMockUser
    @Test
    public void getMyStatisticsTest() throws Exception{
        when(userStatisticsService.getMyStatistics()).thenReturn(stats);
        mockMvc.perform(get("/stats"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/statistics"))
        .andExpect(model().attributeExists("stats"));
    }

    @WithMockUser
    @Test
    public void getGlobalStatistics() throws Exception{
        
        when(userStatisticsService.getAllStatistics()).thenReturn(lStatistics);
        when(gameService.getAllFinishedGames()).thenReturn(lGames);
        mockMvc.perform(get("/global"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/globalStatistics"))
        .andExpect(model().attributeExists("stats"));
    }
    









}
