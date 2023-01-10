package org.springframework.cluedo.statistics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserStatisticsServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserStatisticsRepository userStatisticsRepository;

    User user;
    UserStatistics stats = new UserStatistics();
    
    @BeforeEach
    public void config(){

        user=new User();
        user.setUsername("user1");
        user.setPassword("user1");
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user.setAuthority("user");
        user.setEnabled(1);

        stats.setId(1);
        stats.setUser(user);
        stats.setVictories(20);

    }
    @Test
    void getMyStatistics(){
        when(userStatisticsRepository.findMyStatistics(any(User.class))).thenReturn(stats);
        UserStatistics stats = userStatisticsRepository.findMyStatistics(user);
        assertNotNull(stats);
        assertTrue(stats.getUser().equals(user));
        assertTrue(stats.getVictories()==20);
        assertTrue(stats.getUser().getId()==1);
        assertTrue(stats.getId()==1);
    }
}
