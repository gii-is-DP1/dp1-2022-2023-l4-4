package org.springframework.cluedo.game;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.cluedo.celd.CeldService;
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
    private final String LOBBY="games/lobby";
    private final String ON_GAME="games/onGame";
    private final GameService gameService;
    private final UserService userService;
    private final String DICE_VIEW=""; 
    private TurnService turnService;
    
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
        result.addObject("admin", userService.getUserDetails().getAuthorities().toArray()[0].equals("admin"));
        return result;
    }
    //User

    //H10
    @Transactional(readOnly = true)
    @GetMapping()
    public ModelAndView getAllPublicLobbies(){
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
    public ModelAndView createGame(){
        User user= userService.getLoggedUser().get();
    	Game game = new Game();
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
    		System.out.println(br.getAllErrors().toString());
            return new ModelAndView(CREATE_NEW_GAME, br.getModel());
    	} else {
            game.setStatus(Status.LOBBY);
            game.setLobby(new ArrayList<>(List.of(userService.getLoggedUser().get())));
            gameService.saveGame(game);
    		ModelAndView result = new ModelAndView(LOBBY);
            result.addObject("lobby", game);
    		return result;
    	}
    }

    @Transactional(readOnly = true)
    @GetMapping("/{gameId}/lobby")
    public ModelAndView getLobby(@PathVariable("gameId") Integer gameId){
    	ModelAndView result = new ModelAndView(LOBBY);
        Game game = null;
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        result.addObject("lobby", game);
        return result;
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
            result = new ModelAndView(LOBBY);
            result.addObject("lobby", game);
            return result;
        } else {
            result = new ModelAndView(LOBBY);
            Game copy = new Game();
            BeanUtils.copyProperties(game, copy);
            List<User> ul=copy.getLobby();
            ul.add(loggedUser.get());
            copy.setLobby(ul);
            gameService.saveGame(copy);
            result.addObject("lobby", copy);
            return result;
        }
    }

    // H3
   /*  @Transactional
    @GetMapping("/{game_id}/{host_id}")
    public ModelAndView startGame(@PathVariable("game_id") Integer game_id, @PathVariable("host_id") Integer host_id){
        Optional<Game> game = gameService.getGameById(game_id);
        if(!game.isPresent()){
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The game doesn't exist");
            return result;
        } else if (game.get().getHost().getId()!=host_id){
            ModelAndView result = new ModelAndView(GAME_LISTING);
            result.addObject("message", "The host is incorrect");
            return result;
        } else if (game.get().getLobbySize()<3) {
            ModelAndView result = new ModelAndView(LOBBY);
            result.addObject("message", "The game needs at least 3 players to start");
            return result;
        } else {
            ModelAndView result = new ModelAndView("ON_GAME");
            Game copy = new Game();
            BeanUtils.copyProperties(game.get(), copy);
            gameService.initGame(copy);
            userService.initializePlayers(copy.getLobby(), copy);
		    gameService.saveGame(copy);
           
            return result;
        }   
    }
*/
    @GetMapping("/{gameId}/play/test")
    @Transactional
    public Game testTurn(@PathVariable("gameId") Game game) throws WrongPhaseException,DataNotFound{
        System.out.println(Optional.of(game).get().getId());
        return game;
    }

    @GetMapping("/{gameId}/play/turn")
    @Transactional
    public ModelAndView initTurn(@PathVariable("gameId") Game game) throws WrongPhaseException,DataNotFound{
        Optional<Game> nrGame = Optional.of(game);
        System.out.println("AQUI------------------------------------------------------>"+nrGame.get().getId());
        if(nrGame.isPresent()){
            Integer playerCount;
            if(game.getActualPlayer()==null){
                playerCount= -1;
            }else{
                playerCount = game.getPlayers().indexOf(game.getActualPlayer());
            }
            if (game.getPlayers().size()-1 == playerCount){
                game.setActualPlayer(game.getPlayers().get(0));
                game.setRound(game.getRound()+1);
            }else{
                game.setActualPlayer(game.getPlayers().get(playerCount+1));
            }
            gameService.saveGame(game);
            System.out.println("Llega aquí");
            Turn turn = turnService.createTurn(game.getActualPlayer(),game.getRound());
            System.out.println("Pero aquí no");
            turnService.save(turn);
            
            ModelAndView result = new ModelAndView(DICE_VIEW);
            return result;
        }else{
            throw new DataNotFound();
        }
    }

    @GetMapping("/{id}/play/dices")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class,CorruptGame.class})
    private ModelAndView throwDices(@PathParam("id") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        
        Game game = null;
        ModelAndView result = new ModelAndView(DICE_VIEW);
        try{
            game = gameService.getGameById(gameId);
        } catch(DataNotFound e) {
            result.addObject("message", "The game doesn't exist");
            return result;
        }
        turnService.throwDice(game);
        
        return result;
    }

    @GetMapping("/{gameId}/play/move")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class,CorruptGame.class})
    private ModelAndView movementPosibilities(@PathParam("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
            Game game= gameService.getGameById(gameId);
            ModelAndView result = new ModelAndView(DICE_VIEW);
            result.addObject("movements", turnService.whereCanIMove(game));
            return result;
    }
}
