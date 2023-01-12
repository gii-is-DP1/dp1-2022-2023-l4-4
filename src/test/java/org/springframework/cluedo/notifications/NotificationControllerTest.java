package org.springframework.cluedo.notifications;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.notification.Notification;
import org.springframework.cluedo.notification.NotificationController;
import org.springframework.cluedo.notification.NotificationService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=NotificationController.class,
excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
excludeAutoConfiguration=SecurityConfiguration.class)

public class NotificationControllerTest {


    @MockBean
    NotificationService notificationService;

    @MockBean
    protected UserService userService;

    @Autowired
    protected MockMvc mockMvc;
    private Notification noti1;
    private List<Notification> notifications;
    private User user1;
    private User user2;
    private Game ipGame;
    private List<User> listFriends;

    @BeforeEach
    public void config(){
    
    notifications = new ArrayList<>();
    noti1 = new Notification();
    user1=new User();
    user2=new User();

    

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
    user2.setFriends(new ArrayList<>());
    

    listFriends= new ArrayList<>();
    listFriends.add(user2);
    user1.setFriends(listFriends);

    List<User> list= new ArrayList<>();
        list.add(user1);
        list.add(user2);
        
    ipGame = new Game();
    ipGame.setId(1);
    ipGame.setHost(user1);
    ipGame.setLobbySize(5);
    ipGame.setLobby(list);
    ipGame.setStatus(Status.LOBBY);

    noti1.setId(1);;
    noti1.setLink(2);
    noti1.setReceiver(user1);
    noti1.setSender(user2);
    noti1.setText("HOLA BUENOS DIAS");
    noti1.setTimestamp("10:13");
    notifications.add(noti1);
    
    
    }
    @WithMockUser
    @Test
    public void testGetAllAchievements() throws Exception{
        when(notificationService.getNotificationsByReceiverId(user1.getId())).thenReturn(notifications);
        mockMvc.perform(get("/notifications/"+user1.getId())).
                andExpect(status().isOk()).
                andExpect(view().name("notifications/myNotifications")).
                andExpect(model().attributeExists("notification"));
    }

    @WithMockUser
    @Test
    public void testShowFriendsToInvite() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(userService.findUserFriends(any(Integer.class))).thenReturn(user1.getFriends());
        mockMvc.perform(get("/notifications/"+ipGame.getId()+"/invite")).
                andExpect(status().isOk()).
                andExpect(view().name("notifications/inviteFriends")).
                andExpect(model().attributeExists("user","lobbyId"));
    }

    @WithMockUser
    @Test
    public void testInviteFriend() throws Exception{
        when(userService.getLoggedUser()).thenReturn(Optional.of(user1));
        when(userService.findUserById(any(Integer.class))).thenReturn(Optional.of(user2));
        mockMvc.perform(get("/notifications/"+ipGame.getId()+"/invite/"+user2.getId())).
        andExpect(status().is3xxRedirection());
    }






    
}
