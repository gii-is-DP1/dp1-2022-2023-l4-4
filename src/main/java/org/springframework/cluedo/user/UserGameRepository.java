package org.springframework.cluedo.user;

import org.springframework.data.repository.CrudRepository;

public interface UserGameRepository extends CrudRepository<UserGame, Integer> {
    
}
