package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller

public class GameController {
    

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService=gameService;
    }

    @GetMapping(value = "/games")
    public List<Game> getMyGames
}
