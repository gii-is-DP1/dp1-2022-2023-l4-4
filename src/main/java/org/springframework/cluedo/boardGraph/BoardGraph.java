package org.springframework.cluedo.boardGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.cluedo.celd.Celd;

public class BoardGraph {
    private static Graph<Celd, DefaultEdge> tablero = new SimpleGraph<Celd, DefaultEdge>(DefaultEdge.class);
    
    public BoardGraph(List<Celd> celds, List<List<Celd>> celdConnections) {
        for(Celd celd:celds) {
            tablero.addVertex(celd);
        }
        for(List<Celd> connection:celdConnections) {
            tablero.addEdge(connection.get(0), connection.get(1));
        }
    }

    public Set<Celd> possibleMovements(Integer distancia, Celd celda) {
        Set<Celd> anteriores = new HashSet<Celd>();
        anteriores.add(celda);
        return possibleMovements(tablero, 0 ,distancia, anteriores, new HashSet<Celd>(), new HashSet<Celd>());
    }

    private Set<Celd> possibleMovements(Graph<Celd, DefaultEdge> paths, int contador, Integer distancia, Set<Celd> anteriores,
            Set<Celd> nuevas, Set<Celd> posiblesMovimientos) {
                posiblesMovimientos.addAll(nuevas);
            if(contador < distancia) {
                anteriores.addAll(nuevas);
                nuevas.clear();
                anteriores.forEach(x -> nuevas.addAll(Graphs.neighborSetOf(paths, x)));
                nuevas.removeAll(anteriores);
                contador ++;
                possibleMovements(paths, contador, distancia, anteriores, nuevas, posiblesMovimientos);
            }
        return posiblesMovimientos;
    }
}
