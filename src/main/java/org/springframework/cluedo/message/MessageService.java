package org.springframework.cluedo.message;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
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
          return messageRepository.findAll();
         
        }

        @Transactional
        public void saveMessage(Message m) throws DataAccessException{
            messageRepository.save(m);
        }





}
