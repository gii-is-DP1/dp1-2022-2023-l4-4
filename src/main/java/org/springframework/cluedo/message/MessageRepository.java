package org.springframework.cluedo.message;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  MessageRepository extends CrudRepository<Message, Integer>{
    
    List<Message> findAll();

    @Query("SELECT m FROM Message m WHERE m.game.id = :id")
    List<Message> findByGameId(@Param("id") Integer id);

}
