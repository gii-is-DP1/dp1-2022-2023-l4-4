package org.springframework.cluedo.game;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.celd.CeldService;
import org.springframework.cluedo.exceptions.CorruptGame;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.exceptions.GameNotExists;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {
    
    private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final String DICE_VIEW="";
    private final GameService gameService;   
    private CeldService celdService;
    private TurnService turnService;
private UserService userService;

    @Autowired
    public GameController(GameService gameService, CeldService celdService, TurnService turnService, UserService userService){
        this.gameService=gameService;
        this.celdService=celdService;
        this.turnService = turnService;
        this.userService=userService;
    }
    //Admin
    //H11
    @GetMapping(value = "/admin")
    public ModelAndView getAllActiveGames() {
        ModelAndView result = new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.findAllActiveGames());
        return result;
    }
    //H12
    @GetMapping("/admin/past")
    public ModelAndView getAllPastGames(){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.findAllPastGames());
        return result;
    }
    //User

    //H10
    @GetMapping()
    public ModelAndView getAllActivePublicGames(){
        ModelAndView result=new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.findAllActiveGames());
        return result;
    }
    //H11
    @GetMapping("/past")
    public ModelAndView getAllPastUserGames(@PathVariable("id") Integer id) throws DataNotFound{
        User user= userService.getLoggedUser();
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.findAllPastUserGames(user.getId()));
        return result;
    } 
    //H1
    @GetMapping("/new")
    public ModelAndView createGame(){
        return null;
    }

    @GetMapping("/{id}/play/startTurn")
    @Transactional
   private ModelAndView initTurn(@PathParam("id") Integer gameId) throws WrongPhaseException,GameNotExists{
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
            throw new GameNotExists();
        }
    }

    @GetMapping("/{id}/play/dices")
    @Transactional(rollbackFor = {WrongPhaseException.class,GameNotExists.class,CorruptGame.class})
    private ModelAndView throwDices(@PathParam("id") Integer gameId) throws WrongPhaseException,GameNotExists,CorruptGame{
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
            throw new GameNotExists();
        }
    }

    @GetMapping("/{gameId}/play/move")
    @Transactional(rollbackFor = {WrongPhaseException.class,GameNotExists.class,CorruptGame.class})
    private ModelAndView movementPosibilities(@PathParam("gameId") Integer gameId) throws WrongPhaseException,GameNotExists,CorruptGame{
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
            throw new GameNotExists();
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
