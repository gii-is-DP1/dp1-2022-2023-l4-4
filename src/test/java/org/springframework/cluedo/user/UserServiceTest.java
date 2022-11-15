package org.springframework.cluedo.user;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
    
    @Mock
    UserRepository repo;

    User user;
    

    @BeforeEach
    public void config(){
        
        List<User> users = new ArrayList<>();
        user=new User();
        user.setUsername("user1");
        user.setPassword("user1");
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user.setAuthority("user");
        user.setEnabled(1);
        users.add(user);
        
        
        user.setUsername("user2");
        user.setPassword("user2");
        user.setId(2);
        user.setEmail("user2@gmail.com");
        user.setImageurl("https://i0.wp.com/www.kukyflor.com/blog/wp-content/uploads/2018/04/6820517-tulip-fields.jpg?fit=1280%2C800&ssl=1");
        user.setAuthority("admin");
        user.setEnabled(1);
        users.add(user);

        when(repo.findAll()).thenReturn(users);
        when(repo.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(repo.findByUsername(any(String.class))).thenReturn(Optional.of(user));
    }

    @Test
    void getAllUsers() {
        List<User> allUsers=repo.findAll();
        assertFalse(allUsers.isEmpty());
        assertTrue(allUsers.size()==1);
    }


    @Test
    void findUserById(int id) {
        Optional<User> user = repo.findById(1);
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("user"));
        assertFalse(user.get().getImageurl().isEmpty());
    }

    @Test
    void findUserByUsername(String username) {
        Optional<User> user = repo.findByUsername("user2");
        assertNotNull(user);
        assertTrue(user.get().getUsername().equals("user2"));
        assertTrue(user.get().getPassword().equals("user2"));
        assertTrue(user.get().getEmail().equals("user2@gmail.com"));
        assertTrue(user.get().getAuthority().equals("user"));
        assertFalse(user.get().getImageurl().isEmpty());
    }
    
    @Test
    void getLoggedUser() {
        
    }

}


/*@Transactional
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}


	@Transactional
	public void saveUser(User user) throws DataAccessException {
		userRepository.save(user);
	}
	
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}

	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> getLoggedUser(){
		UserDetails a=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(a.getUsername());*/