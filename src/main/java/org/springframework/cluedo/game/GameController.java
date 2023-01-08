package org.springframework.cluedo.game;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.Accusation;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.cluedo.accusation.FinalAccusation;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.card.CardService;
import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGameService;
import org.springframework.cluedo.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cluedo.exceptions.CorruptGame;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.message.Message;
import org.springframework.cluedo.message.MessageService;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.cluedo.enumerates.CardName;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/games")
public class GameController {
    
    private static final String ACCUSATION_LIST = "games/accusationListing";
	private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final String CREATE_NEW_GAME="games/createNewGame";
    private final String NOTES_VIEW = "games/notes";
    private final String LOBBY="games/lobby";
    private final String ON_GAME="games/onGame";
    private final String DICE_VIEW="games/diceView"; 
    private final String MOVE_VIEW = "games/selectCeld";
    private final String ACCUSATION_VIEW="games/makeAccusation";
    private final String FINAL_DECISION_VIEW="games/makeFinalDecision";
    private final String FINAL_ACCUSATION_VIEW="games/makeFinalAccusation";
    private final String SELECT_CARD_TO_SHOW = "games/showCard";
    private GameService gameService;
    private UserService userService;
    private TurnService turnService;
    private CardService cardService;
    private AccusationService accusationService;
    private UserGameService userGameService;
    private MessageService messageService;
    
    @Autowired
    public GameController(GameService gameService, TurnService turnService, UserService userService, CardService cardService, AccusationService accusationService, UserGameService userGameService, MessageService messageService){
        this.gameService=gameService;
        this.turnService = turnService;
        this.userService=userService;
        this.cardService = cardService;
        this.accusationService = accusationService;
        this.userGameService = userGameService;
        this.messageService=messageService;

    }
    
    @ModelAttribute("privateList")
    private List<Boolean> privateList(){
        return List.of(true,false);
    }

    @ModelAttribute("nPlayers")
    private List<Integer> nPlayers(){
        return List.of(3,4,5,6);
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/admin/active")
    public ModelAndView getAllActiveGames() {
        ModelAndView result = new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.getAllNotFinishedGames());
        return result;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/admin/past")
    public ModelAndView getAllPastGames(){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.getAllFinishedGames());
        result.addObject("admin", true);
        return result;
    }
    
    @Transactional(readOnly = true)
    @GetMapping()
    public ModelAndView getAllPublicLobbies(){
        User user= userService.getLoggedUser().get();
        Game game = gameService.getMyNotFinishedGame(user);
        if(game != null) {
            if(game.getStatus().equals(Status.LOBBY)) {
                return new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
            } else {
                return new ModelAndView("redirect:/games/"+game.getId()+"/play");
            }
        }
        ModelAndView result=new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.getAllPublicLobbies());
        return result;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/past")
    public ModelAndView getAllPastUserGames(){
        User user= userService.getLoggedUser().get();
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.getMyFinishedGames(user));
        return result;
    }
    
    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView initNewGame(){
        User user= userService.getLoggedUser().get();
        Game game = gameService.getMyNotFinishedGame(user);
        if(game != null) {
            if(game.getStatus().equals(Status.LOBBY)) {
                return new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
            } else {
                return new ModelAndView("redirect:/games/"+game.getId()+"/play");
            }
        }
        game = new Game();
    	ModelAndView result = new ModelAndView(CREATE_NEW_GAME);
        result.addObject("game", game);
        result.addObject("user", user);
        result.addObject("status", Status.LOBBY);
        return result;
    }
    
    @Transactional
    @PostMapping("/new")
    public ModelAndView formNewGame(@Valid Game game, BindingResult br) {
        
        if(br.hasErrors()) {
            return new ModelAndView(CREATE_NEW_GAME, br.getModel());
    	} else {
            game.setStatus(Status.LOBBY);
            game.setLobby(new ArrayList<>(List.of(userService.getLoggedUser().get())));
            gameService.saveGame(game);
    		ModelAndView result = new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
    		return result;
    	}
    }

    @Transactional(readOnly = true)
    @GetMapping("/{gameId}/chat")
    public ModelAndView getChat(@PathVariable("gameId") Integer gameId)throws DataNotFound{
        ModelAndView mav = new ModelAndView("games/chat");
        User userNow = userService.getLoggedUser().get();
        List<Message> nrMessages = messageService.getAllMessageByGameId(gameId);
        if(nrMessages.size()>=0){
            mav.addObject("messages", nrMessages);
            mav.addObject("gameId",gameId);
            mav.addObject("chatMessage", new Message());
            mav.addObject("userNowId", userNow.getId());
            return mav;
        }
        
        throw new DataNotFound();

    }
    @PostMapping("/{gameId}/chat")
    public ModelAndView newMessage(@Valid Message message, BindingResult br, @PathVariable("gameId") Integer gameId) throws DataNotFound{
        if(!br.hasErrors()){
            this.messageService.saveMessage(message);
        }
        ModelAndView result = new ModelAndView("redirect:chat");
        return result;
    } 


    @Transactional(readOnly = true)
    @GetMapping("/{gameId}/lobby")
    public ModelAndView getLobby(@PathVariable("gameId") Integer gameId, RedirectAttributes ra, HttpServletResponse response){
    	ModelAndView result = null;
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result = new ModelAndView("redirect:/games");
            ra.addFlashAttribute("message", "The game doesn't exist or the host left the game");
            return result;
        }
        User user= userService.getLoggedUser().get();

        if(!game.getLobby().contains(user)) {
            return new ModelAndView("redirect:/games");
        } else if (game.getHost().equals(user)){
            result = new ModelAndView(LOBBY);
            result.addObject("canStart", game.getLobby().size()<3?false:true);
        } else {
            result = new ModelAndView(LOBBY);
        }
        //response.addHeader("Refresh", "4");
        result.addObject("lobby", game);
        return result;
    }

    @Transactional()
    @GetMapping("/{gameId}/leave")
    public ModelAndView leaveGame(@PathVariable("gameId") Integer gameId) {
        Game game = null;
        ModelAndView result = new ModelAndView("redirect:/games");
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            return result;
        }
        User user= userService.getLoggedUser().get();
        if(game.getLobby().contains(user)){
            if(game.getStatus().equals(Status.LOBBY)) {
                gameService.deleteUserFromLobby(user, game);
            } else if (game.getStatus().equals(Status.IN_PROGRESS)) { 
                gameService.leaveGameInProgress(user,game);
            }
        }
        return result;
    }
    
    @Transactional
    @GetMapping("/{gameId}")
    public ModelAndView joinGame(@PathVariable("gameId") Integer gameId) throws DataNotFound{
        Optional<User> loggedUser = userService.getLoggedUser();
        ModelAndView result = new ModelAndView(GAME_LISTING);
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result.addObject("message", "The game doesn't exist");
            return result;
        }
       
        if(game.getStatus()!=Status.LOBBY){
            result.addObject("message", "The game is started");
            return result;
        } else if(game.getLobby().contains(loggedUser.get())) {
            result= new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
            result.addObject("lobby", game);
            return result;
        } else if(game.getLobby().size()==game.getLobbySize()) {
            result.addObject("message", "The lobby is full");
            return result;
        } else {
            result = new ModelAndView(LOBBY);
            Game copy = new Game();
            BeanUtils.copyProperties(game, copy);
            copy.addLobbyUser(loggedUser.get());
            gameService.saveGame(copy);
            result= new ModelAndView("redirect:/games/"+copy.getId()+"/lobby");
            result.addObject("lobby", copy);
            return result;
        }
    }

    @Transactional
    @PostMapping("/{gameId}/lobby")
    public ModelAndView startGame(@PathVariable("gameId") Integer gameId){
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        Optional<User> loggedUser = userService.getLoggedUser();
        if (game.getHost()!=loggedUser.get()){
            ModelAndView result = new ModelAndView(LOBBY);
            result.addObject("lobby",game);
            result.addObject("message", "The host is incorrect");
            return result;
        } else if (game.getLobby().size()<3) {
            ModelAndView result = new ModelAndView(LOBBY);
            result.addObject("lobby",game);
            result.addObject("message", "The game needs at least 3 players to start");
            return result;
        } else {
            Game copy = new Game();
            BeanUtils.copyProperties(game, copy);
            gameService.initGame(copy);
		    gameService.saveGame(copy);
            turnService.createTurn(copy.getActualPlayer(),copy.getRound());
            return new ModelAndView("redirect:/games/"+copy.getId()+"/play");
        }   
    }

    

    @GetMapping("/{gameId}/play")
    @Transactional
    public ModelAndView playGame(@PathVariable("gameId") Integer gameId, HttpServletResponse response) throws WrongPhaseException,DataNotFound{
        
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView("redirect:/games");
            return result;
        }
        
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        Turn actualTurn = turnService.getActualTurn(game).get();
        Optional<Accusation> nrAccusation =  accusationService.thisTurnAccusation(actualTurn);
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            if(actualTurn.getPhase().equals(Phase.ACCUSATION) && nrAccusation.isPresent() && nrAccusation.get().getPlayerWhoShows().getUser().equals(nrLoggedUser.get())) {
                return new ModelAndView("redirect:/games/"+gameId+"/play/showcard");
            } else {
                //response.addHeader("Refresh", "3");
                ModelAndView result = new ModelAndView(ON_GAME);
                result.addObject("game", game);
                return result;
            }
        }
        
        switch(actualTurn.getPhase()){
            case DICE:return new ModelAndView("redirect:/games/"+gameId+"/play/dice");
            case MOVEMENT:return new ModelAndView("redirect:/games/"+gameId+"/play/move");
            case ACCUSATION:{
                if(accusationService.thisTurnAccusation(actualTurn).isEmpty()){
                    return new ModelAndView("redirect:/games/"+gameId+"/play/accusation");
                }else{
                    //response.addHeader("Refresh", "3");
                    ModelAndView result = new ModelAndView(ON_GAME);
                    result.addObject("game",game);
                    return result;
                }
            }
            case FINAL:return new ModelAndView("redirect:/games/"+gameId+"/play/finish");
            default: {
                //response.addHeader("Refresh", "3");
                ModelAndView result = new ModelAndView(ON_GAME);
                result.addObject("game", game);
                return result;
            }
        }
    }

    public ModelAndView wrongStatus(Game game) {
        //Devuelve la pantalla correspondiente en función del estado de la partida.
        ModelAndView result = null;
        if(game.getStatus().equals(Status.LOBBY)){
            result = new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
        return result;
        } else {
            result = new ModelAndView("redirect:/games");
        }
        return result;
    }

    public ModelAndView notYourTurn(Game game) {
        //Redirige al usuario a la pantalla correspondiente si no es su turno
        ModelAndView result = new ModelAndView("redirect:/games/"+game.getId()+"/play"); //TODO: pantalla default para expectadores de la partida
        result.addObject("message", "Wait until it's your turn");
        return result;
    }

    public ModelAndView checkPhase(Game game,Phase phase){
        if (!turnService.getActualTurn(game).get().getPhase().equals(phase)){
            ModelAndView result = new ModelAndView("redirect:/games/"+game.getId()+"/play");
            result.addObject("message", "Wrong Phase");
            return result; 
        }
        return null;
    }
    
    @GetMapping("/{gameId}/play/accusationList")
    @Transactional
    public ModelAndView viewAccusations(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
         Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView("redirect:/games");
            return result;
        }
        ModelAndView result= new ModelAndView(ACCUSATION_LIST);
        result.addObject("accusations",accusationService.getAllAcusationsByGame(game));
        return result;
    }
        
    @GetMapping("/{gameId}/play/dice")
    @Transactional
    public ModelAndView throwDicesButton(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView("redirect:/games");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }

        ModelAndView result=checkPhase(game,Phase.DICE); 
        if (result==null){
            result = new ModelAndView(DICE_VIEW);
            result.addObject("gameId", gameId);
        }
        return result;
    } 
    
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    @PostMapping("/{gameId}/play/dice")
    public ModelAndView throwDices(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }

        try{
            turnService.throwDice(game);
        } catch(Exception e) {
            ModelAndView result = new ModelAndView(ON_GAME);
            result.addObject("game", game);
            result.addObject("message", "This is not your turn");
            return result;
        } 
         
        return new ModelAndView("redirect:/games/"+game.getId()+"/play");
    } 

    @GetMapping("/{gameId}/play/move")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView movementPosibilities(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        ModelAndView result = new ModelAndView(MOVE_VIEW);
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }

        result.addObject("diceResult", turnService.getActualTurn(game).get().getDiceResult());
        result.addObject("celdForm",new CeldForm());
        result.addObject("movements", turnService.whereCanIMove(game).stream().collect(Collectors.toList()));
        return result;
    }

    @PostMapping("/{gameId}/play/move")
    @Transactional
    public ModelAndView moveTo(@PathVariable("gameId") Integer gameId,CeldForm finalCeld, BindingResult br) throws WrongPhaseException,DataNotFound{
        
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING); 
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }
        Turn turn=turnService.getActualTurn(game).get();
        turn.setFinalCeld(finalCeld.getFinalCeld());
        if(turn.getFinalCeld().getCeldType()!=CeldType.CORRIDOR && turn.getFinalCeld().getCeldType()!=CeldType.CENTER) {
            turn.setPhase(Phase.ACCUSATION);
        } else {
            turn.setPhase(Phase.FINAL);
        }
        turnService.saveTurn(turn);
        return new ModelAndView("redirect:/games/"+game.getId()+"/play");
    }
    
    @GetMapping("/{gameId}/play/accusation")
    @Transactional
    public ModelAndView makeAccusationButton(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView("redirect:/games");
            return result;
        }
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        ModelAndView result=checkPhase(game,Phase.ACCUSATION); 
        if (result==null){
            Optional<Accusation> nrAccusation= accusationService.thisTurnAccusation(turnService.getActualTurn(game).get());
            if (nrAccusation.isEmpty()){
                if(!gameService.isUserTurn(nrLoggedUser, game)){
                    notYourTurn(game);
                }else{
                result = new ModelAndView(ACCUSATION_VIEW);
                Accusation accusation = new Accusation();
                accusation.setTurn(turnService.getActualTurn(game).get());
                result.addObject("suspects", cardService.getAllSuspects());
                result.addObject("weapons", cardService.getAllWeapons());
                result.addObject("room", cardService.getCardByCardName(CardName.valueOf(accusation.getTurn().getFinalCeld().getCeldType().toString())));
                result.addObject("accusation", accusation);
                }
            }
        }
        return result;
    }

    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    @PostMapping("/{gameId}/play/accusation")
    public ModelAndView makeAccusation(@PathVariable("gameId") Integer gameId, @Valid Accusation accusation) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }
        try{
            System.out.println("LLEGA");
            accusation.setPlayerWhoShows(userGameService.whoShouldGiveCard(game,accusation));
            System.out.println("TAMBIEN");
            accusationService.saveAccusation(accusation);
            if (accusation.getPlayerWhoShows()==null){
                System.out.println("AQUI NOPE");
                turnService.makeAccusation(game); 
            }
        } catch(Exception e) {
            ModelAndView result = new ModelAndView(ON_GAME);
            result.addObject("game", game);
            result.addObject("message", "This is not your turn");
            return result;
        }
         
        return new ModelAndView("redirect:/games/"+game.getId()+"/play");
    } 

    @GetMapping("/{gameId}/play/finish")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView initFinishTurn(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }
        ModelAndView result=checkPhase(game,Phase.FINAL); 
        if (result==null){
            result = new ModelAndView(FINAL_DECISION_VIEW);
            result.addObject("game", game);
            return result;
        }
        return result;
    }

    @GetMapping("/{gameId}/play/finalAccusation")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView initFinalAccusation(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }
        ModelAndView result=checkPhase(game,Phase.FINAL); 
        if (result==null){
            result=new ModelAndView(FINAL_ACCUSATION_VIEW); 
            FinalAccusation finalAccusation = new FinalAccusation();
            finalAccusation.setTurn(turnService.getActualTurn(game).get());
            result.addObject("suspects", cardService.getAllSuspects());
            result.addObject("weapons", cardService.getAllWeapons());
            result.addObject("rooms", cardService.getAllRooms());
            result.addObject("finalAccusation", finalAccusation);
        }
        return result;
    }

    @GetMapping("/{gameId}/play/showcard")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView showCard(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }
        
        Optional<User> nrLoggedUser=userService.getLoggedUser();
        Turn actualTurn = turnService.getActualTurn(game).get();
        Optional<Accusation> nrAccusation =  accusationService.thisTurnAccusation(actualTurn);
        if(actualTurn.getPhase().equals(Phase.ACCUSATION) && nrAccusation.isPresent() && nrAccusation.get().getPlayerWhoShows().getUser().equals(nrLoggedUser.get())) {
            ModelAndView result = new ModelAndView(SELECT_CARD_TO_SHOW);
            List<Card> cardsToShow = accusationService.getMatchingCardsFromUser(nrAccusation.get(), nrAccusation.get().getPlayerWhoShows());
            result.addObject("cards", cardsToShow);
            result.addObject("cardForm",new CardForm());
            return result;
        } else {
            ModelAndView result = new ModelAndView(ON_GAME);
            result.addObject("game", game);
            return result;
        }
    }

    @PostMapping("/{gameId}/play/showcard")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView submitShowCard(@PathVariable("gameId") Integer gameId, @Valid CardForm cardForm) throws WrongPhaseException,DataNotFound,CorruptGame{
         Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }
        
        ModelAndView result=checkPhase(game,Phase.ACCUSATION); 

        if (result==null){
            accusationService.showCard(turnService.getActualTurn(game).get(),cardForm.cardSelected);
            turnService.makeAccusation(game);
            return new ModelAndView("redirect:/games/"+game.getId()+"/play");
        }

        return result; 
    }

    @PostMapping("/{gameId}/play/finalAccusation")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView makeFinalAccusation(@PathVariable("gameId") Integer gameId, @Valid FinalAccusation finalAccusation) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }

        ModelAndView result=checkPhase(game,Phase.FINAL); 

        if (result==null){
            gameService.makeFinalAccusation(game, finalAccusation);
            return new ModelAndView("redirect:/games/"+game.getId()+"/play");
        }

        return result; 
    }
    
    @PostMapping("/{gameId}/play/finish")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView finishTurn(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        }

        if(!gameService.isGameInProgress(game)) {
            return wrongStatus(game);
        }

        Optional<User> nrLoggedUser=userService.getLoggedUser();
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }
        ModelAndView result=checkPhase(game,Phase.FINAL); 
        if (result==null){
            gameService.finishTurn(game);
            return new ModelAndView("redirect:/games/"+game.getId()+"/play");

        }
        return result;
    }
    
    @GetMapping("/{gameId}/play/notes")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    public ModelAndView newNotes(){
        return new ModelAndView(NOTES_VIEW);
    }
}
 