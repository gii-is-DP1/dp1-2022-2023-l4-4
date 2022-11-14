package org.springframework.cluedo.celd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.boardGraph.BoardGraph;
import org.springframework.stereotype.Service;

@Service
public class CeldService {
    @Autowired
    CeldRepository celdRepository;

    public CeldService(CeldRepository cRepository) {
        this.celdRepository=cRepository;
    }

    public List<Celd> getAllCelds() {
        return celdRepository.findAll();
    }

    public List<List<Celd>> getAllPairs() {
        List<Celd> celds = celdRepository.findAll();
        List<List<Celd>> connections = new ArrayList<List<Celd>>();
        for(Celd celd:celds) {
            for(Celd con:celd.getConnectedCelds()) {
                List<Celd> connection = new ArrayList<Celd>();
                connection.add(con);
                connection.add(celd);
                connections.add(connection);
            }
        }
        return connections;
    }

    public Set<Celd> getAllPossibleMovements(Integer pasos, Celd celd, BoardGraph board) {
        return board.possibleMovements(pasos, celd);
    }

    public Celd getById(int i) {
        return celdRepository.findById(i).get();
    }
}
