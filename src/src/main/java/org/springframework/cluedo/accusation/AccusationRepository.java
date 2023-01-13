package org.springframework.cluedo.accusation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccusationRepository extends CrudRepository<Accusation, Integer> {
    
    @Query("SELECT a FROM Accusation a WHERE a.turn.id = :turnId")
    public Optional<Accusation> findByTurn(@Param("turnId") Integer turnId);

    @Query("SELECT a FROM Accusation a WHERE a.turn.userGame.game.id=:gameId")
    public List<Accusation> findAllByGame(@Param("gameId") Integer gameId);
}
 