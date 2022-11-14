package org.springframework.cluedo.turn;

import java.util.Optional;

import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.UserGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TurnRepository extends CrudRepository<Turn,Integer> {
    
    public Optional<Turn> findById(Integer id);
    /*   
    @Query("SELECT t FROM Turn t WHERE t.userGameId=:userGameId AND t.round=:round")
    public Optional<Turn> getTurn(@Param("userGameId")Integer userGameId, @Param("round")Integer round);
    
    @Query("SELECT COUNT t FROM turn t WHERE t.round=MAX(SELECT t FROM turn t JOIN usergame u ON t.userGame=u WHERE u.userGameId=:game.id) AND t IN (SELECT t FROM turn t JOIN usergame u ON t.userGame=u WHERE u.userGameId=:game.id)")
    public Integer whatPlayerGo(@Param("game") Game game);
    
    @Query("SELECT t.userGame FROM Turn t WHERE t.userGame.game=:game AND t.round=MAX(SELECT t.userGame.round FROM Turn t WHERE t.userGame.game=:game) AND t.order=MAX(SELECT t.userGame.order FROM Turn t WHERE t.round=MAX(SELECT t.userGame.round FROM Turn t WHERE t.userGame.game=:game)")
    public UserGame whatPlayerGo2(@Param("game") Game game);

    @Query("SELECT tf.userName FROM (SELECT t FROM Turn t JOIN UserGame u ON t.userGame=u JOIN Game g ON u.game=g GROUP BY t.id HAVING u.game.id=:game.id WHERE t.round=MAX(t.round)) tf WHERE tf.userGame.orderUser=MAX(tf.userGame.order)")
    public UserGame whatPlayerGo(@Param("game") Game game);

    @Query("SELECT tf.userGame.orderUser FROM(SELECT t FROM Turn t INNER JOIN UserGame u ON t.userGame=u INNER JOIN Game g ON u.game=g GROUP BY t.id HAVING g.id=:game.id WHERE t.round=MAX(t.round)) tf WHERE tf.userGame.orderUser=MAX(tf.userGame.orderUser)")
    public Integer whatPlayerGo(@Param("game") Game game);

*/
    @Query(nativeQuery = true, value="SELECT count(t.id) FROM  SELECT (Turn t JOIN user_games u ON t.user_game_id=u.id GROUP BY t.id HAVING u.game_id=:game.id) t WHERE t.round=max(t.round)")
    public Integer whatPlayerGo(@Param("game") Game game);

    public Turn save(Turn t);

}
