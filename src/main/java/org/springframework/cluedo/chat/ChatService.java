package org.springframework.cluedo.chat;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository){
        this.chatRepository=chatRepository;
    }

    @Transactional
    public Chat getChatByGameId(Integer id){
        return chatRepository.findChatByGameId(id);
    }

    @Transactional
    public void save(Chat c){
        chatRepository.save(c);
    }

    @Transactional
    public void edit(Chat c){
        Chat toUpdate = chatRepository.findById(c.getId()).get();
        toUpdate.setId(c.getId());
        toUpdate.setGame(c.getGame());
        toUpdate.setMessages(c.getMessages());
        save(c);
    }

}