package org.springframework.cluedo.game;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/games")
public class GameController {
    
    private final String GAME_LISTING="games/gameList";
    private final String GAME_PAST_LISTING="games/gamePastList";
    private final String CREATE_NEW_GAME="games/createNewGame";
    private final String LOBBY="games/lobby";
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService=gameService;
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
    	//Game game = gameService.
    }
}
