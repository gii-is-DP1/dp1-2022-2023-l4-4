package org.springframework.cluedo.celd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
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

    @GetMapping("/init")
    public void init() {
        this.board = new BoardGraph(celdService.getAllCelds(), celdService.getAllPairs());
        Set<Celd> vertex = board.getAllVertex();
        for(Celd celd:vertex) {
            System.out.println(celd.getId());
        }
        Set<DefaultEdge> edges = board.getAllEdges();
        edges.forEach(x->System.out.println(x));
    }

    @GetMapping("/move")
    public void move() {
        Celd celd = celdService.getById(1);
        System.out.println(celd.getId());
        Set<Celd> s = celdService.getAllPossibleMovements(3, celd,board);
        s.forEach(x->System.out.println(x.getId()));
    }

}
