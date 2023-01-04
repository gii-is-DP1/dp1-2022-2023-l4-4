package org.springframework.cluedo.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat,Integer>{

    List<Chat> findAll();

    @Query("SELECT c FROM Chat c WHERE c.game.id = :id")
    Chat findChatByGameId(@Param("id") Integer id);


}