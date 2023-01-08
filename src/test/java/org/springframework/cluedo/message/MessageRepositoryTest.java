package org.springframework.cluedo.message;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameRepository;
import org.springframework.cluedo.game.GameService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;


@DataJpaTest
public class MessageRepositoryTest {

    @Mock
    MessageRepository repo;

    @Mock
    protected GameService gameService;

    List<Message> messages = new ArrayList<Message>();
    @BeforeEach
    public void config() throws DataNotFound{

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

        Message m1 = new Message();
        m1.setText("Mensaje test 1");
        m1.setId(1);
        m1.setGame(game1);
        m1.setPlayer(user1);
        messages.add(m1);
    }

    @Test
    public void testFindAll(){
        when(repo.findAll()).thenReturn(messages);
        List<Message> test = repo.findAll();
        assertNotNull(test);
        assertTrue(test.size()==1);
    }

    @Test
    public void testFindByGameId(){
        when(repo.findByGameId(100)).thenReturn(messages);
        List<Message> testMessages1=repo.findByGameId(100);
        System.out.println(testMessages1.size());
        assertTrue(testMessages1.size()==1);
        assertTrue(testMessages1.get(0).equals(messages.get(0)));    
    }

    @Test
    public void testNotFindByGameId(){
        List<Message> testMessages2=repo.findByGameId(0);
        assertFalse(testMessages2.size()!=0);
        }

    
    
    
}
