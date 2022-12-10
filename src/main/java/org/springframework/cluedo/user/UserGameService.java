package org.springframework.cluedo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGameService {
    @Autowired
    UserGameRepository userGameRepository;
    
    public UserGame saveUserGame(UserGame userGame) {
        return userGameRepository.save(userGame);
    }
}
