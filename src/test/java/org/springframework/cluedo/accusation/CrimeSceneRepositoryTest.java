package org.springframework.cluedo.accusation;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
@DataJpaTest
public class CrimeSceneRepositoryTest {
  
    @Autowired
    CrimeSceneRepository repo;

    @Test
    public void testFindByGame(){
        CrimeScene accusations = repo.findByGame(1);
        assertNotNull(accusations);
    }
}
