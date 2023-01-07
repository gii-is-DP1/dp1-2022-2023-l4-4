package org.springframework.cluedo.turn;


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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;


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
