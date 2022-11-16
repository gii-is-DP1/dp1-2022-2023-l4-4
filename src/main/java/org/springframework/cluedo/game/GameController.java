package org.springframework.cluedo.game;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserGame;
import org.springframework.cluedo.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private CeldService celdService;
    private TurnService turnService;
    
    @Autowired
    public GameController(GameService gameService, CeldService celdService, TurnService turnService, UserService userService){
        this.gameService=gameService;
        this.celdService=celdService;
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
        result.addObject("gameId", 0);
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
        game.setStatus(Status.LOBBY);
        game.setLobby(new ArrayList<>(List.of(userService.getLoggedUser().get())));
        if(br.hasErrors()) {
    		return new ModelAndView(CREATE_NEW_GAME, br.getModel());
    	} else {
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
        result.addObject("lobby", gameService.getGameById(gameId).get());
        return result;
    }
    
    // H2
    @Transactional
    @PostMapping()
    public ModelAndView joinGame(@RequestParam("gameId") Integer game_id) throws DataNotFound{
        Optional<User> loggedUser = userService.getLoggedUser();
    	Optional<Game> game = gameService.getGameById(game_id);
        ModelAndView result = new ModelAndView(GAME_LISTING);
        if(!game.isPresent()){
            result.addObject("message", "The game doesn't exist");
            return result;
        } else if(game.get().getStatus()!=Status.LOBBY){
            result.addObject("message", "The game is started");
            return result;
        } else if(game.get().getLobby().size()==game.get().getLobbySize()) {
            result.addObject("message", "The lobby is full");
            return result;
        } else if(game.get().getLobby().contains(loggedUser.get())) {
            result = new ModelAndView(LOBBY);
            result.addObject("lobby", game.get());
            return result;
        } else {
            result = new ModelAndView(LOBBY);
            Game copy = new Game();
            BeanUtils.copyProperties(game.get(), copy);
            copy.getLobby().add(loggedUser.get());
            gameService.saveGame(copy);
            result.addObject("lobby", copy);
            return result;
        }
    }

    // H3
    @Transactional
    @PutMapping("/{game_id}/{host_id}")
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
            copy.setStatus(Status.IN_PROGRESS);
            copy.setDuration(Duration.ofMinutes(0));
            copy.setCrimeScene(null); // No terminado
            copy.setRound(1);
            
            List<SuspectType> suspects=new ArrayList<>();
            for (SuspectType value : SuspectType.values()) {
                suspects.add(value);
            }
            
            for (User user : game.get().getLobby()) {
                Integer available = suspects.size();
                UserGame userGame = new UserGame();
                userGame.setAccusationsNumber(0);
                userGame.setGame(copy);
                userGame.setUser(user);
                userGame.setIsAfk(false);
                Integer randomInt = ThreadLocalRandom.current().nextInt(available)+1;
                userGame.setSuspect(suspects.get(randomInt));
                suspects.remove(suspects.get(randomInt));
                userGame.setCards(null);
                copy.getPlayers().add(userGame);
            }
            gameService.saveGame(copy);
            return result;
        }   
    }


    @GetMapping("/{id}/play/turn")
    @Transactional
   private ModelAndView initTurn(@PathParam("id") Integer gameId) throws WrongPhaseException,DataNotFound{
        Optional<Game> nrGame = gameService.getGameById(gameId);
        if(nrGame.isPresent()){
            Game game=nrGame.get();
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
            Turn turn = turnService.createTurn(game.getActualPlayer(),game.getRound());
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
        Optional<Game> nrGame = gameService.getGameById(gameId);
        if(nrGame.isPresent()){
            Game game= nrGame.get();
            Optional<Turn> nrTurn=turnService.getTurn(game.getActualPlayer(), game.getRound());
            if(nrTurn.isPresent()){
                Turn turn=nrTurn.get();
                turn=turnService.throwDice(turn);
                turnService.save(turn);
            ModelAndView result = new ModelAndView(DICE_VIEW);
            return result;
            }else{
                throw new CorruptGame();
            }
        }else{
            throw new DataNotFound();
        }
    }

    @GetMapping("/{gameId}/play/move")
    @Transactional(rollbackFor = {WrongPhaseException.class,DataNotFound.class,CorruptGame.class})
    private ModelAndView movementPosibilities(@PathParam("gameId") Integer gameId) throws WrongPhaseException,DataNotFound,CorruptGame{
        System.out.println(gameId);
        Optional<Game> nrGame = gameService.getGameById(gameId);
        if(nrGame.isPresent()){
            Game game= nrGame.get();
            Optional<Turn> nrTurn=turnService.getTurn(game.getActualPlayer(), game.getRound());
            if(nrTurn.isPresent()){
                Turn turn=nrTurn.get();
            ModelAndView result = new ModelAndView(DICE_VIEW);
            System.out.println(celdService.getAllPossibleMovements(turn.getDiceResult(), turn.getInitialCeld()));
            result.addObject("movements", celdService.getAllPossibleMovements(turn.getDiceResult(), turn.getInitialCeld()));
            return result;
            }else{
                throw new CorruptGame();
            }
        }else{
            throw new DataNotFound();
        }
    }

    /*@PostMapping("{id}/play/move")
    @Transactional(rollbackFor = {WrongPhaseException.class,GameNotExists.class,CorruptGame.class})
    private ModelAndView movement(@PathParam("id") Integer gameId,Celd celd) throws WrongPhaseException,GameNotExists,CorruptGame{
        Optional<Game> nrGame = gameService.getGameById(gameId);
        if(nrGame.isPresent()){
            Game game= nrGame.get();
            Optional<Turn> nrTurn=turnService.getTurn(game.getActualPlayer(), game.getRound());
            if(nrTurn.isPresent()){
                Turn turn=nrTurn.get();
            ModelAndView result = new ModelAndView(DICE_VIEW);
            result.addObject("movements", celdService.getAllPossibleMovements(turn.getDiceResult(), turn.getInitialCeld()));
            return result;
            }else{
                throw new CorruptGame();
            }
        }else{
            throw new GameNotExists();
        }
    }*/
}
