package org.springframework.cluedo.game;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.CeldService;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.turn.Turn;
import org.springframework.cluedo.turn.TurnService;
import org.springframework.stereotype.Controller;
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
    @Autowired
    public GameController(GameService gameService, CeldService celdService, TurnService turnService){
        this.gameService=gameService;
        this.celdService=celdService;
        this.turnService = turnService;
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
    @GetMapping("/{id}")
    public ModelAndView getAllPastGames(@PathVariable("id") Integer id){
        ModelAndView result = new ModelAndView(GAME_PAST_LISTING);
        result.addObject("games", gameService.findAllPastUserGames(id));
        return result;
    }
    //H1
    @GetMapping("/new")
    public ModelAndView createGame(){
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
