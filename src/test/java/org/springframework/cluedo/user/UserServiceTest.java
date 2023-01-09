package org.springframework.cluedo.user;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
    
    @Mock
    UserRepository repo;

    UserService userService;

    @Mock
    UserGameService userGameService;
    
    User user1;
    User user2;
    List<User> users = new ArrayList<>();
    List<Achievement> achievements = new ArrayList<>();
    UserStatistics stats = new UserStatistics();
    @BeforeEach
    public void config(){

        Achievement achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("achievement1");
        achievement.setXp(100);
        achievement.setMetric(Metric.EXPERIENCE);
        achievement.setDescription("Achievement for test");
        achievement.setGoal(1000);
        achievement.setBadgeType(Badge.BRONZE);
        achievements.add(achievement);

        user1=new User();
        user1.setUsername("user1");
        user1.setPassword("user1");
        user1.setId(1);
        user1.setEmail("user1@gmail.com");
        user1.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user1.setAuthority("user");
        user1.setEnabled(1);
        user1.setTag("#ABCD3333");
        user1.setFriends(new ArrayList<>());
        users.add(user1);

        user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("user2");
        user2.setId(2);
        user2.setEmail("user2@gmail.com");
        user2.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user2.setAuthority("admin");
        user2.setEnabled(1);
        user2.setFriends(new ArrayList<>());
        users.add(user2); 

        stats.setId(1);
        stats.setUser(user1);
        stats.setVictories(20);

        userService = new UserService(repo, userGameService);
    }
    
    @Test
    void testGetAllUsers() {
        when(repo.findAll()).thenReturn(users);
        List<User> allUsers=userService.getAllUsers();
        assertFalse(allUsers.isEmpty());
        assertTrue(allUsers.size()==2);
    }


    @Test
    void testFindUserById() {
        when(repo.findById(any(Integer.class))).thenReturn(Optional.of(user2));
   
        Optional<User> user = userService.findUserById(1);
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("admin"));
        assertFalse(user.get().getImageurl().isEmpty());
    }

    @Test
    void testFindUserByUsername() {
        when(repo.findByUsername(any(String.class))).thenReturn(Optional.of(user2));
        Optional<User> user = userService.findUserByUsername("user2");
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("admin"));
        assertFalse(user.get().getImageurl().isEmpty());
    }

    @Test
    public void testSaveUser() {
        when(repo.save(any(User.class))).thenReturn(user2);
        User userToSave = new User();
        userToSave.setId(1);
        userToSave.setPassword("test1");
        userToSave.setUsername("test");
        userToSave.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        userToSave.setEmail("test@gmail.com");
        userToSave.setAuthority("admin");
        userToSave.setEnabled(1);
        
        userService.saveUser(userToSave);
        verify(repo, times(1)).save(userToSave);
        
    }


    @Test
    public void testGetXUsersFails(){
        List<User> userslist = userService.getXUsers(-1);
        assertNull(userslist);
    }

    @Test
    public void testGetXUsersSuccessPageHigherOrEqualsThanZero(){
        when(repo.findAll()).thenReturn(users);
        List<User> userslist = userService.getXUsers(0);
        assertNotNull(userslist);
        assertTrue(userslist.size()==2);
    }

    @Test
    public void testGetXUsersSuccessPageMinHigherThanSize(){
        when(repo.findAll()).thenReturn(users);
        List<User> userslist = userService.getXUsers(1);
        assertNotNull(userslist);
        assertTrue(userslist.size()==0);
    }

    @Test
    public void testFindUserByTag() {
        when(repo.findByTag(any(String.class))).thenReturn(user1);
        User user = userService.findUserByTag("#ABCD3333");
        assertNotNull(user);
        assertTrue(user.getTag().equals("#ABCD3333"));
    }

    @Test
    public void testDeleteUser(){
        userService.deleteUser(1);
        verify(repo,times(1)).deleteById(1);
    }

    @Test
    public void testFindUserFriends(){
        when(repo.findFriendsById(any(Integer.class))).thenReturn(user1.getFriends());
        List<User> friends = userService.findUserFriends(1);
        assertNotNull(friends);
        assertTrue(friends.isEmpty());
    }

    @Test
    public void testInitializePlayers() {
        Game game = new Game();
        game.setId(1);
        game.setPlayers(new ArrayList<>());

        userService.initializePlayers(users, game);
        assertTrue(game.getPlayers().size()==2);
    }

}