package org.springframework.cluedo.statistics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserRepository;

@DataJpaTest
public class UserStatisticsRepositoryTest {

    @Autowired
    protected UserStatisticsRepository userStatisticsRepository;

    @Mock
    protected UserRepository userRepository;

    User user;

    @BeforeEach
    public void init(){
        user = new User();
        user.setId(1);
    }

    @Test
    public void testFindMyStatistics(){
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        Optional<User> user = userRepository.findByUsername("manuel333");
        UserStatistics stats = userStatisticsRepository.findMyStatistics(user.get());
        assertNotNull(stats);
    }
    
    @Test
    public void TestSetNullUserStatistic(){
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        Optional<User> user = userRepository.findByUsername("1");
        userStatisticsRepository.deleteUserStatisticByUserId(1);
        UserStatistics stats = userStatisticsRepository.findMyStatistics(user.get());
        assertNull(stats);
        assertThrows(NullPointerException.class, () -> {userStatisticsRepository.findMyStatistics(user.get()).getUser();});
    }
}
