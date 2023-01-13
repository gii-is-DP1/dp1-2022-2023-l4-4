package org.springframework.cluedo.accusation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FinalAccusationRepository extends CrudRepository<FinalAccusation,Integer>{
    
    @Query("SELECT fa FROM FinalAccusation fa WHERE fa.turn.id=:turnId")
    public FinalAccusation findByTurn(@Param("turnId") Integer turnId);
    
}
