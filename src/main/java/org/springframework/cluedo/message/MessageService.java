package org.springframework.cluedo.message;


import java.time.Duration;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

        @Autowired
        MessageRepository messageRepository;


        @Autowired
        public MessageService(MessageRepository repository){
            this.messageRepository= repository;
        }

        @Transactional(readOnly = true)
        public List<Message> getAllMessageByGameId(Integer id){
            return messageRepository.findByGameId(id);
        }

        @Transactional(readOnly = true)
        public List<Message> getAll(){
            List<Message> m = messageRepository.findAll();
            return m;
        }

        @Transactional
        public void saveMessage(Message m) throws DataAccessException{
            messageRepository.save(m);
        }





}
