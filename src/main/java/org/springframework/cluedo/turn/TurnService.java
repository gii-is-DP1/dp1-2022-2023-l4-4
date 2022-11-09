package org.springframework.cluedo.turn;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {
    
    private TurnRepository turnRepository;

    @Autowired
    private TurnService (TurnRepository turnRepository){
        this.turnRepository=turnRepository;
    }

    public Turn throwDice(Turn turn) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.DICE){
            throw new WrongPhaseException();
        }
        Integer result = ThreadLocalRandom.current().nextInt(6)+1;
        result +=ThreadLocalRandom.current().nextInt(6)+1;
        turn.setDiceResult(result);
        turn.setPhase(Phase.MOVEMENT);
        return save(turn);
    } 

    public Turn makeAcusation(Turn turn) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.ACUSATION){
            throw new WrongPhaseException();
        }
        turn.setPhase(Phase.MOVEMENT);
        return save(turn);
    }

    @Transactional
    public Turn save(Turn turn){
        turnRepository.save(turn);
        return turn;
    }
}
