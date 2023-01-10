package org.springframework.cluedo.accusation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CrimeSceneRepository extends CrudRepository<CrimeScene,Integer>{
    
    @Query("SELECT cs FROM CrimeScene cs WHERE cs.game.id=:gameId")
    public CrimeScene findByGame(@Param("gameId") Integer gameId);
    
}
