package org.springframework.cluedo.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.Test;


@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    MessageRepository repo;

    @Test
    public void testFindAll(){
        List<Message> test = repo.findAll();
        assertNotNull(test);
        assertTrue(test.size()==1);
    }

    @Test
    public void testFindByGameId(){
        List<Message> testMessages1=repo.findByGameId(1);
        assertTrue(testMessages1.size()==1);
        assertTrue(testMessages1.get(0).getText().equals("Bienvenido"));    
    }

    @Test
    public void testNotFindByGameId(){
        List<Message> testMessages2=repo.findByGameId(0);
        assertFalse(testMessages2.size()!=0);
        }

    @Test
    public void testFindAllByUserId(){
        List<Message> messages = repo.findAllByUserId(1);
        assertFalse(messages.isEmpty());
    }    

    @Test
    public void testSetNullUser(){
        repo.setNullUser(1);
        List<Message> messages = repo.findAllByUserId(1);
        assertTrue(messages.isEmpty());
    }
    
    
}
