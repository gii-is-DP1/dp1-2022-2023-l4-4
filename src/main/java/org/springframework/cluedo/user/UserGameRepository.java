package org.springframework.cluedo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.cluedo.game.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserGameRepository extends CrudRepository<UserGame,Integer>{
    @Query("SELECT ug FROM UserGame ug WHERE ug.game=:game AND ug.orderUser=:order")
    Optional<UserGame> findPlayerByGameAndOrder(@Param("game")Game game, @Param("order")Integer order);

    @Query("SELECT ug FROM UserGame ug WHERE ug.game=:game")
    List<UserGame> findPlayerByGame(@Param("game")Game game);

    @Query("SELECT ug FROM UserGame ug WHERE ug.game=:game AND ug.isEliminated=false")
    List<UserGame> remainingPlayersNotEliminated(@Param("game")Game game);
    
    @Query("SELECT ug FROM UserGame ug WHERE ug.user = :user AND ug.game = :game")
    UserGame findUsergameByGameAndUser(@Param("game")Game game, @Param("user")User user);

    @Modifying
    @Query("UPDATE UserGame u SET u.user=null WHERE user.id = :id")
    void setNullUser(@Param("id")Integer id);

    @Query("SELECT u FROM UserGame u WHERE u.user.id = :id")   
    List<UserGame> findUserGameByGameId(@Param("id")Integer id);
}
