package org.springframework.cluedo.accusation;

import org.springframework.cluedo.game.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CrimeSceneRepository extends CrudRepository<CrimeScene,Integer>{
    
    @Query("SELECT cs FROM CrimeScene cs WHERE cs.game=:game")
    public CrimeScene findByGame(@Param("game") Game game);
    
}
