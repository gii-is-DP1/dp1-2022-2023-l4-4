package org.springframework.cluedo.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/games")
public class GameController {
    
    private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService=gameService;
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

}
