package org.springframework.cluedo.celd;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.boardGraph.BoardGraph;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/celd")
public class CeldController {

    private CeldService celdService;
    private BoardGraph board;

    @Autowired
    public CeldController(CeldService celdService) {
        this.celdService = celdService;
    }

    public void init() {
        this.board = new BoardGraph(celdService.getAllCelds(), celdService.getAllPairs());
        System.out.println("INIT");
    } 

    @GetMapping("/move")
    public Set<Celd> move(Celd celd, Integer dices) {
        if (board==null){
            init();
        }
        return celdService.getAllPossibleMovements(dices, celd,board);
    }

}
