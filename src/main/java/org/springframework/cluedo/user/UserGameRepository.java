package org.springframework.cluedo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.cluedo.game.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserGameRepository extends CrudRepository<UserGame,Integer>{
    @Query("SELECT ug FROM UserGame ug WHERE ug.game=:game AND ug.orderUser=:order")
    Optional<UserGame> findPlayerByGameAndOrder(@Param("game")Game game, @Param("order")Integer order);

   // @Query("SELECT ug FROM UserGame ug LEFT JOIN FinalAccusation f WHERE ug.game=:game AND f.id=null")
    @Query("SELECT ug FROM UserGame ug WHERE ug.game=:game AND ug.isEliminated=false")
    List<UserGame> remainingPlayersNotEliminated(@Param("game")Game game);

    @Query("SELECT ug FROM UserGame ug WHERE ug.user.id = :id")
    UserGame getUserGameByUserId(@Param("id") Integer id);
}
