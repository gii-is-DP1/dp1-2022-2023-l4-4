package org.springframework.cluedo.game;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.CeldService;
import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cluedo.exceptions.CorruptGame;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {
    
    private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final String CREATE_NEW_GAME="games/createNewGame";
    private final String LOBBY_HOST="games/lobby";
    private final String LOBBY_PLAYER="games/lobbyPlayer";
    private final String ON_GAME="games/onGame";
    private final String DICE_VIEW="games/diceView"; 
    private final String MOVE_VIEW = "games/selectCeld";
    private final String ACCUSATION_VIEW="games/makeAccusation";
    private final GameService gameService;
    private final UserService userService;
    private final TurnService turnService;
    
    @Autowired
    public GameController(GameService gameService, TurnService turnService, UserService userService){
        this.gameService=gameService;
        this.turnService = turnService;
        this.userService=userService;
    }
    //Admin
    //H12
    @Transactional(readOnly = true)
    @GetMapping(value = "/admin/active")
    public ModelAndView getAllActiveGames() {
        ModelAndView result = new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.getAllNotFinishedGames());
        return result;
    }
    //H13
    @Transactional(readOnly = true)
    @GetMapping("/admin/past")
    public ModelAndView getAllPastGames(){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.getAllFinishedGames());
        result.addObject("admin", true);
        return result;
    }
    //User
    //H10
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
    //H11
    @GetMapping("/past")
    public ModelAndView getAllPastUserGames(){
        User user= userService.getLoggedUser().get();
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.getMyFinishedGames(user));
        return result;
    }
    
    //H1
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
        List<Boolean> bool = new ArrayList<>();
        bool.add(true);
        bool.add(false);
        result.addObject("privateList", bool);
        result.addObject("nPlayers", List.of(3,4,5,6));
        result.addObject("game", game);
        result.addObject("user", user);
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
    		ModelAndView result = new ModelAndView("redirect:"+game.getId()+"/lobby");
    		return result;
    	}
    }

    @Transactional(readOnly = true)
    @GetMapping("/{gameId}/lobby")
    public ModelAndView getLobby(@PathVariable("gameId") Integer gameId){
    	ModelAndView result = null;
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result = new ModelAndView("redirect:/games");
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        User user= userService.getLoggedUser().get();

        if(!game.getLobby().contains(user)) {
            return new ModelAndView("redirect:/games");
        } else if (game.getHost().equals(user)){
            result = new ModelAndView(LOBBY_HOST);
        } else {
            result = new ModelAndView(LOBBY_PLAYER);
        }
        
        result.addObject("lobby", game);
        return result;
    }

    @Transactional
    @GetMapping("/{gameId}/leave")
    public String leaveGame(@PathVariable("gameId") Integer gameId) {
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            return "redirect:/games";
        }
        User user= userService.getLoggedUser().get();
        if(game.getLobby().contains(user)){
            if(game.getStatus().equals(Status.LOBBY)) {
                gameService.deleteUserFromLobby(user, game);
            } else if (game.getStatus().equals(Status.IN_PROGRESS)) {
                gameService.leaveGameInProgress(user,game);
            }
        }
        return "redirect:/games";
    }
    
    // H2
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
        } else if(game.getLobby().size()==game.getLobbySize()) {
            result.addObject("message", "The lobby is full");
            return result;
        } else if(game.getLobby().contains(loggedUser.get())) {
            result= new ModelAndView("redirect:/games/"+game.getId()+"/lobby");
            result.addObject("lobby", game);
            return result;
        } else {
            result = new ModelAndView(LOBBY_HOST);
            Game copy = new Game();
            BeanUtils.copyProperties(game, copy);
            List<User> ul=copy.getLobby();
            ul.add(loggedUser.get());
            copy.setLobby(ul);
            gameService.saveGame(copy);
            result= new ModelAndView("redirect:/games/"+copy.getId()+"/lobby");
            result.addObject("lobby", copy);
            return result;
        }
    }

    // H3
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
            ModelAndView result = new ModelAndView(LOBBY_HOST);
            result.addObject("lobby",game);
            result.addObject("message", "The host is incorrect");
            return result;
        } else if (game.getLobby().size()<3) {
            ModelAndView result = new ModelAndView(LOBBY_HOST);
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
    public ModelAndView playGame(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound{
        
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
            ModelAndView result = new ModelAndView(ON_GAME);
            result.addObject("game", game);
            return result;
        }
        //TODO: implementar pantalla final
        switch(turnService.getActualTurn(game).get().getPhase()){
            case DICE:return new ModelAndView("redirect:/games/"+gameId+"/play/dice");
            case MOVEMENT:return new ModelAndView("redirect:/games/"+gameId+"/play/move");
            case ACCUSATION:return new ModelAndView("redirect:/games/"+gameId+"/play/accusation");
            case FINAL:return new ModelAndView("redirect:/games/"+gameId+"/play/finish");
            default: {
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
        if(turn.getFinalCeld().getCeldType()!=CeldType.CORRIDOR) {
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
        
        if(!gameService.isUserTurn(nrLoggedUser, game)){
            return notYourTurn(game);
        }

        ModelAndView result=checkPhase(game,Phase.ACCUSATION); 
        if (result==null){
            result = new ModelAndView(ACCUSATION_VIEW);
            result.addObject("gameId", gameId);
        }
        return result;
    } 

    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class})
    @PostMapping("/{gameId}/play/accusation")
    public ModelAndView makeAccusation(@PathVariable("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
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
            turnService.makeAccusation(game);
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
}
 