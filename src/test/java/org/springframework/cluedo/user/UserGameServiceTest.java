package org.springframework.cluedo.user;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserGameServiceTest {
    
    @Mock
    protected UserGameRepository userGameRepo;
    protected UserGameService userGameService;

    @Test
    public void testSaveUserGame() {
        when(userGameService.saveUserGame(null));
    }
}