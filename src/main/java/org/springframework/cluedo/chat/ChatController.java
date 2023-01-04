package org.springframework.cluedo.chat;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameService;
import org.springframework.cluedo.message.MessageService;
import org.springframework.cluedo.statistics.GlobalStatistics;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.UserService;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.message.Message;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

    public static final String SHOW_CHAT = "games/chat";

    @Autowired
    MessageService messageService;

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @Autowired
    UserGameService userGameService;

    @Autowired
    GameService gameService;
    
    @Autowired
    public ChatController(MessageService service, ChatService chatService, UserService userService, GameService gameService, UserGameService userGameService){
        this.messageService = service;
        this.chatService=chatService;
        this.userService=userService;
        this.gameService=gameService;
        this.userGameService=userGameService;
    }

    @GetMapping("/newchat/{id}")
    public ModelAndView initNewChat(@PathVariable("id") Integer gameId) throws DataNotFound{
        if(chatService.getChatByGameId(gameId)==null){
            Chat c = new Chat();
            Game game = gameService.getGameById(gameId);
            c.setId(gameId);
            c.setGame(game);
            chatService.save(c);
        }
        ModelAndView result=new ModelAndView("redirect:/chat/{id}");
        return result;
    }

    @GetMapping("/chat/{id}")
    public ModelAndView chatView(@PathVariable("id") Integer chatId) throws DataNotFound{
        Chat c = chatService.getChatByGameId(chatId);
        User userNow = userService.getLoggedUser().get();
        ModelAndView result = new ModelAndView(SHOW_CHAT);
        result.addObject("userNow", userNow);
        result.addObject("chat", c);
        result.addObject("message", new Message());
        return result;
    }

    @PostMapping("/chat/{id}")
    public ModelAndView newMessage(@PathVariable("id") Integer chatId, BindingResult br, @Valid Message message){
        if(br.hasErrors()){
            return new ModelAndView(SHOW_CHAT, br.getModel());
        }
        else{
        Chat c = chatService.getChatByGameId(chatId);
        User userNow = userService.getLoggedUser().get();
        UserGame userGameNow = userGameService.getUserGameByUserId(userNow.getId());
        
        message.setPlayer(userGameNow);
        message.setText(userNow.getUsername()+": "+message);
        List<Message> messages = c.getMessages();
        messages.add(message);
        c.setMessages(messages);
        chatService.edit(c);
        ModelAndView result = new ModelAndView("redirect:/chat/{id}");
        result.addObject("chat", c);
        result.addObject("userNow", userNow);
        result.addObject("message", new Message());
        return result;}
    }

}