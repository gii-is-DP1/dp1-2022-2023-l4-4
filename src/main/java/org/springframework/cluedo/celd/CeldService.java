package org.springframework.cluedo.celd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.boardGraph.BoardGraph;
import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.stereotype.Service;

@Service
public class CeldService {
    
    CeldRepository celdRepository;

    BoardGraph board;

    @Autowired
    public CeldService(CeldRepository cRepository) {
        this.celdRepository=cRepository;
    }

    public List<Celd> getAllCelds() {
        return celdRepository.findAll();
    }

    public void init() {
        board = new BoardGraph(getAllCelds(), getAllPairs());
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

    public Celd getCenter(){
        return celdRepository.findCenter();
    }

    public Set<Celd> getAllPossibleMovements(Integer pasos, Celd celd) {
        if (board==null){
            init();
        }
        return board.possibleMovements(pasos, celd);
    }

    public Celd getById(int i) throws DataNotFound{
        Optional<Celd> nrCeld = celdRepository.findById(i);
        if(nrCeld.isPresent()){
            return nrCeld.get();
        }else{
            throw new DataNotFound();
        }
    }

    public Celd getByCeldType(CeldType celdType){
        return celdRepository.findByCeldType(celdType);
    }
}
