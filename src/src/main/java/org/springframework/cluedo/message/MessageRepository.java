package org.springframework.cluedo.message;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  MessageRepository extends CrudRepository<Message, Integer>{
    
    List<Message> findAll();

    @Query("SELECT m FROM Message m WHERE m.game.id = :id")
    List<Message> findByGameId(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Message m SET m.player=null WHERE m.player.id = :id")
    void setNullUser(@Param("id")Integer id);

    @Query("SELECT m FROM Message m Where m.player.id = :id")
    List<Message> findAllByUserId(Integer id);

}
