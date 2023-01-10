package org.springframework.cluedo.accusation;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
public class AccusationRepositoryTest {
  
    @Autowired
    AccusationRepository repo;

    @Test
    public void testFindByTurn(){
        Optional<Accusation> accusation = repo.findByTurn(1);
        assertTrue(accusation.isPresent());
        accusation = repo.findByTurn(0);
        assertFalse(accusation.isPresent());
        
    }

    @Test
    public void testFindAllByGame(){
        List<Accusation> accusations = repo.findAllByGame(1);
        assertNotNull(accusations);
        assertFalse(accusations.isEmpty());
    }
}
