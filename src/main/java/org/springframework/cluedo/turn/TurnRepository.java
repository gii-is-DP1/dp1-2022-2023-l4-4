package org.springframework.cluedo.turn;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TurnRepository extends CrudRepository<Turn,Integer> {
    
    public Optional<Turn> findById(Integer id);
       
    @Query("SELECT t FROM Turn t WHERE t.userGame.id=:userGameId AND t.round=:round")
    public Optional<Turn> getTurn(@Param("userGameId")Integer userGameId, @Param("round")Integer round);

}
