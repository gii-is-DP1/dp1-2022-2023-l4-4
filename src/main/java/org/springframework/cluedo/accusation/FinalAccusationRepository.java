package org.springframework.cluedo.accusation;

import org.springframework.cluedo.turn.Turn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FinalAccusationRepository extends CrudRepository<FinalAccusation,Integer>{
    
    @Query("SELECT fa FROM FinalAccusation fa WHERE fa.turn=:turn")
    public FinalAccusation findByTurn(@Param("turn") Turn turn);
    
}
