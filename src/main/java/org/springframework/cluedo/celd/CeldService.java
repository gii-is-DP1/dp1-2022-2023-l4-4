package org.springframework.cluedo.celd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.boardGraph.BoardGraph;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.stereotype.Service;

@Service
public class CeldService {
    @Autowired
    CeldRepository celdRepository;

    BoardGraph board;

    public CeldService(CeldRepository cRepository) {
        this.celdRepository=cRepository;
    }

    public List<Celd> getAllCelds() {
        return celdRepository.findAll();
    }

    public BoardGraph init() {
        return new BoardGraph(getAllCelds(), getAllPairs());
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
}
