package org.springframework.cluedo.game;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.Status;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.CeldService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {
    
    private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final String CREATE_NEW_GAME="games/createNewGame";
    private final String LOBBY="games/lobby";
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
    //H11
    @Transactional(readOnly = true)
    @GetMapping(value = "/admin")
    public ModelAndView getAllActiveGames() {
        ModelAndView result = new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.findAllActiveGames());
        return result;
    }
    //H12
    @Transactional(readOnly = true)
    @GetMapping("/admin/past")
    public ModelAndView getAllPastGames(){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.findAllPastGames());
        return result;
    }
    //User

    //H10
    @Transactional(readOnly = true)
    @GetMapping()
    public ModelAndView getAllActivePublicGames(){
        ModelAndView result=new ModelAndView(GAME_LISTING);
        result.addObject("games", gameService.findAllActiveGames());
        return result;
    }
    //H11
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ModelAndView getAllPastGames(@PathVariable("id") Integer id){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.findAllPastUserGames(id));
        return result;
    }
    
    //H1
    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView createGame(){
    	Game game = new Game();
    	ModelAndView result = new ModelAndView(CREATE_NEW_GAME);
        result.addObject("game", game);
        return result;
    }
    
    @Transactional
    @PostMapping("/new")
    public ModelAndView formNewGame(@Valid Game game, BindingResult br) {
    	if(br.hasErrors()) {
    		return new ModelAndView(CREATE_NEW_GAME, br.getModel());
    	} else {
    		gameService.saveGame(game);
    		ModelAndView result = new ModelAndView(LOBBY);
    		return result;
    	}
    }
    
    // H2
    @Transactional
    @PutMapping("/{game_id}")
    public ModelAndView joinGame(@PathVariable("game_id") Integer game_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User loggedUser = userService.findUser(username).get();
    	Optional<Game> game = gameService.findById(game_id);
        ModelAndView result = new ModelAndView(GAME_LISTING);
        if(!game.isPresent()){
            result.addObject("message", "The game doesn't exist");
            return result;
        } else if(game.get().getStatus()!=Status.LOBBY){
            result.addObject("message", "The game is started");
            return result;
        } else if(game.get().getPlayers().size()==game.get().getPlayersNumber()) {
            result.addObject("message", "The lobby is full");
            return result;
        }else {
            result = new ModelAndView(LOBBY);
            Game copy = new Game();
            BeanUtils.copyProperties(game.get(), copy);
            copy.getPlayers().add(loggedUser);
            gameService.saveGame(copy);
            return result;
        }
    }

    // H3
    @Transactional
    @PutMapping("/{game_id}/{host_id}")
    public ModelAndView startGame(@PathVariable("game_id") Integer game_id, @PathVariable("host_id") Integer host_id){
        return null;
    }


    @GetMapping("{id}/play/dices")
    public Integer prueba(@PathVariable("id") Integer gameId){
        Game game = gameService.getGameById(gameId).get();
        Integer a= turnService.whatPlayerGo(game);
        System.out.println(a);
        return a;
    }
    
    /*private ModelAndView throwDices(@PathParam("id") Integer gameId) throws WrongPhaseException{
        Game game = gameService.getGameById(gameId).get();
        turnService.whatPlayerGo(game);
        turnService.createTurn(gameService.getGameById(gameId).get().get,turn);
        turnService.throwDice(turn);
        ModelAndView result = new ModelAndView(DICE_VIEW);
        return result;
    }
*/
}
