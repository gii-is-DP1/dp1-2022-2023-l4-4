package org.springframework.cluedo.user;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.cluedo.web.CurrentUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;



@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTest {

    private static final int TEST_USER_ID = 1;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserStatisticsService userStatisticsService;
    @MockBean
    private CurrentUser currentUser;
    
    private User prueba;

    @BeforeEach
    void setup(){
        prueba=new User();
        prueba.setEmail("prueba@email.com");
        prueba.setUsername("prueba123");
        prueba.setPassword("password");
        prueba.setAuthority("user");
        prueba.setId(TEST_USER_ID);
        prueba.setTag("tag_prueba");
        given(this.userService.findUserById(TEST_USER_ID)).willReturn(Optional.of(prueba));
        given(this.currentUser.getCurrentUser()).willReturn("prueba");
    }

    @WithMockUser(value = "spring")
    @Test
    public void showUserListTest() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk())
        .andExpect(model().attributeExists("users"))
        .andExpect(view().name("/users/userList"));
    }

}
