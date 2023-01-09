package org.springframework.cluedo.user;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.notification.NotificationService;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=UserController.class,
excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
excludeAutoConfiguration=SecurityConfiguration.class)
public class UserControllerTest {
    
    @MockBean
    protected UserService userService;

    @MockBean
    protected NotificationService notificationService;

    @MockBean
    protected UserStatisticsService statisticsService;

    @Autowired
    protected MockMvc mockMvc;

    List<User> users;
    User user1;
    User user2;

    @BeforeEach
    public void config(){
        users = new ArrayList<>();
        
        user1 = new User();
        user1.setId(1);
        user1.setUsername("test 1");
        user1.setPassword("test 1");
        user1.setTag("#FRDG2222");
        user1.setAuthority("user");
        user1.setEnabled(1);
        user1.setEmail("user1@gmail.com");
        user1.setFriends(new ArrayList<>());
        user1.setAchievements(new ArrayList<>());
        
        
        user2 = new User();
        user2.setId(1);
        user2.setUsername("test 2");
        user2.setPassword("test 2");
        user2.setTag("#FRDG3333");
        user2.setAuthority("user");
        user2.setEnabled(1);
        user2.setEmail("user2@gmail.com");
        user2.setFriends(new ArrayList<>());
        user2.setAchievements(new ArrayList<>());

        user2.addFriend(user1);
        user1.addFriend(user2);

        users.add(user1);
        users.add(user2);
    }

    @WithMockUser
    @Test
    public void testShowUserList() throws Exception{
        when(userService.getXUsers(any(Integer.class))).thenReturn(users);
        mockMvc.perform(get("/users/paginable/{page}",0))
            .andExpect(status().isOk())
            .andExpect(view().name("users/userList"))
            .andExpect(model().attributeExists("users"));
    }

    @WithMockUser
    //@Test
    public void testShowUserUserPresent() throws Exception{
        when(userService.findUserById(any(Integer.class))).thenReturn(Optional.of(users.get(0)));
        mockMvc.perform(get("/users/{userId}",1))
            .andExpect(status().isOk())
            .andExpect(view().name("users/userDetails"))
            .andExpect(model().attributeExists("user"));
    }

    @WithMockUser
    @Test
    public void testRedirectFriends() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(users.get(0)));
        mockMvc.perform(get("/users/friends"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/users/"+userService.getLoggedUser().get().getId()+"/friends"));
    }

    @WithMockUser
    @Test
    public void testInitDeleteFriendForm() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(users.get(0)));  
        mockMvc.perform(get("/users/{userId}/friends/delete",1))
        .andExpect(status().isOk())
        .andExpect(view().name("users/deleteFriend"))
        .andExpect(model().attributeExists("UsernameForm"));
    }

    @WithMockUser
    @Test
    public void testProcessDeleteFriendFormEmpty() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(users.get(0)));
        mockMvc.perform(post("/users/{userId}/friends/delete",1)
            .with(csrf())
            .param("username", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("users/deleteFriend"))
        .andExpect(model().attributeExists("UsernameForm","message"));
    }

    @WithMockUser
    @Test
    public void testProcessDeleteFriendFormFriendNotExists() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(users.get(0)));
        mockMvc.perform(post("/users/{userId}/friends/delete",1)
            .with(csrf())
            .param("username", "test"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/deleteFriend"))
        .andExpect(model().attributeExists("UsernameForm","message"));
    }

    @WithMockUser
    @Test
    public void testProcessDeleteFriendFormFriendNotInFriendsList() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(users.get(0)));
        when(userService.findUserByUsername(any(String.class))).thenReturn(Optional.of(user1));
        mockMvc.perform(post("/users/{userId}/friends/delete",1)
            .with(csrf())
            .param("username", "test 1"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/deleteFriend"))
        .andExpect(model().attributeExists("UsernameForm","message"));
    }

    @WithMockUser
    @Test
    public void testProcessDeleteFriendFormFriendSuccess() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(userService.findUserByUsername(any(String.class))).thenReturn(Optional.of(user2));
        mockMvc.perform(post("/users/{userId}/friends/delete",1)
            .with(csrf())
            .param("username", "test 2"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/"+1+"/friends"))
        .andExpect(model().attributeExists("user"));
    }

    @WithMockUser
    @Test
    public void testInitAddFriendForm() throws Exception{
        mockMvc.perform(get("/users/{userId}/friends/add",1))
        .andExpect(status().isOk())
        .andExpect(view().name("users/addFriend"))
        .andExpect(model().attributeExists("TagForm"));
    }

    @WithMockUser
    @Test
    public void testProcessAddFriendFormEmpty() throws Exception {
        mockMvc.perform(post("/users/{userId}/friends/add", 1)
        .with(csrf())
        .param("tag", ""))
        .andExpect(view().name("users/addFriend"))
        .andExpect(model().attributeExists("TagForm", "message"));
    }

    @WithMockUser
    @Test
    public void testProcessAddFriendFormFriendNotExists() throws Exception{
        when(userService.findUserByTag(any(String.class))).thenReturn(null);
        mockMvc.perform(post("/users/{userId}/friends/add",1)
            .with(csrf())
            .param("tag", "2"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/addFriend"))
        .andExpect(model().attributeExists("TagForm","message"));
    }

    @WithMockUser
    @Test
    public void testProcessAddFriendFormFriendWithYourself() throws Exception {
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        mockMvc.perform(post("/users/{userId}/friends/add",1)
            .with(csrf())
            .param("tag", "#FRDG3333"))
        .andExpect(view().name("users/addFriend"))
        .andExpect(model().attributeExists("TagForm", "message"));
    }

    @WithMockUser
    @Test
    public void testProcessAddFriendFormSuccess() throws Exception{
        user1.setFriends(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(userService.findUserByTag(any(String.class))).thenReturn(user2);
        mockMvc.perform(post("/users/{userId}/friends/add",1)
            .with(csrf())
            .param("tag", "#FRDG3333"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/1/friends"));
    }

    @WithMockUser
    @Test
    public void testShowUserFriends() throws Exception{
        when(userService.findUserFriends(any(Integer.class))).thenReturn(user1.getFriends());
        mockMvc.perform(get("/users/{userId}/friends",1))
        .andExpect(status().isOk())
        .andExpect(view().name("users/userFriends"))
        .andExpect(model().attributeExists("user"));
    }

    @WithMockUser
    @Test
    public void testInitUpdateUserForm() throws Exception{
        when(userService.findUserById(any(Integer.class))).thenReturn(Optional.of(user1));
        mockMvc.perform(get("/users/{userId}/edit",1))
        .andExpect(status().isOk())
        .andExpect(view().name("users/updateOtherUserForm"));
    }

    @WithMockUser
    @Test
    public void testInitCreationForm() throws Exception{
        mockMvc.perform(get("/users/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/createOrUpdateUserForm"))
        .andExpect(model().attributeExists("user"));
    }


    @WithMockUser
    @Test
    public void testDeleteUser() throws Exception{
        mockMvc.perform(get("/users/{userId}/delete",1))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/paginable/0"));
    }


}
