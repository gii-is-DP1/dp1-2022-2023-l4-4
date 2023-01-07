package org.springframework.cluedo.accusation;

import java.util.List;
import java.util.Optional;

import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.turn.Turn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccusationRepository extends CrudRepository<Accusation, Integer> {
    
    @Query("SELECT a FROM Accusation a WHERE a.turn = :turn")
    public Optional<Accusation> findByTurn(@Param("turn") Turn turn);

    @Query("SELECT a FROM Accusation a WHERE a.turn.userGame.game=:game")
    public List<Accusation> findAllByGame(@Param("game") Game game);
}
