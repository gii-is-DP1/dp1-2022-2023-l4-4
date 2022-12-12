package org.springframework.cluedo.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers=GameController.class,
    excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
    excludeAutoConfiguration=SecurityConfiguration.class)
public class GameControllerTest {
    
    @MockBean
    protected GameService gameService; 

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    public void testGetAllActiveGames() throws Exception{
        mockMvc.perform(get("/admin/active")).
                andExpect(status().isOk()).
                andExpect(view().name("gameListing")).
                andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastGames() throws Exception{
        mockMvc.perform(get("/admin/past")).
                andExpect(status().isOk()).
                andExpect(view().name("Finished games")).
                andExpect(model().attributeExists("games")).
                andExpect(model().attributeExists("admin"));
    }

    @WithMockUser
    @Test
    public void testGetAllPublicLobbies() throws Exception{
        mockMvc.perform(get("")).
                andExpect(status().isOk()).
                andExpect(view().name("gameListing")).
                andExpect(model().attributeExists("games"));
    }

    @WithMockUser
    @Test
    public void testGetAllPastUserGames() throws Exception{
        mockMvc.perform(get("/admin/past")).
                andExpect(status().isOk()).
                andExpect(view().name("Finished games")).
                andExpect(model().attributeExists("games"));
    }

}
