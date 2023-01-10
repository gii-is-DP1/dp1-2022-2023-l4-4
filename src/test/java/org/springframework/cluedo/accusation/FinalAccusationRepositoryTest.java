package org.springframework.cluedo.accusation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
public class FinalAccusationRepositoryTest {
  
    @Autowired
    FinalAccusationRepository repo;

    @Test
    public void testFindByTurn(){
        FinalAccusation finalAccusation = repo.findByTurn(1);
        assertNotNull(finalAccusation);
        finalAccusation = repo.findByTurn(0);
        assertNull(finalAccusation);
        
    }
}