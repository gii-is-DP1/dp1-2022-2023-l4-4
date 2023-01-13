package org.springframework.cluedo.turn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TurnRepositoryTest {


    @Autowired
    protected TurnRepository repo;

    @Test
    public void findTunrByIdTest(){
        Optional<Turn> turn = repo.findById(1);
        assertTrue(turn.isPresent());
        Optional<Turn> turn2 = repo.findById(100);
        assertFalse(turn2.isPresent());
    }

    @Test
    public void findTunrByUserGameIdAndroundTest(){
        Optional<Turn> turn = repo.getTurn(1, 1);
        assertTrue(turn.isPresent());
        Optional<Turn> turn2 = repo.getTurn(100, 100);
        assertFalse(turn2.isPresent());
    }





    
}
