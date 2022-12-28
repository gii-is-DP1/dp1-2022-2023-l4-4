package org.springframework.cluedo.game;

import java.util.List;


import org.springframework.cluedo.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends CrudRepository<Game,Integer>{
     List<Game> findAll() throws DataAccessException;

     @Query("select g from Game g where g.status=org.springframework.cluedo.enumerates.Status.LOBBY AND g.isPrivate = false")
     List<Game> findAllPublicLobbies();
     
     @Query("select g From Game g WHERE :user MEMBER OF g.lobby AND g.status=org.springframework.cluedo.enumerates.Status.FINISHED")
     List<Game> findMyFinishedGames(@Param("user") User user);
     
     @Query("select g from Game g where g.status NOT LIKE org.springframework.cluedo.enumerates.Status.FINISHED")
     List<Game> findAllNotFinishedGames();
     
     @Query("select g from Game g WHERE g.status=org.springframework.cluedo.enumerates.Status.FINISHED")
     List<Game> findAllFinishedGames();
     @Query("select g from Game g where :user member of g.lobby and g.status!=org.springframework.cluedo.enumerates.Status.FINISHED")
     Game getMyNotFinishedGame(@Param("user") User user);
     
}
