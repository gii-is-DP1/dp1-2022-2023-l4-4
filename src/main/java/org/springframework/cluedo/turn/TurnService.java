package org.springframework.cluedo.turn;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.AccusationController;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {
    
    private TurnRepository turnRepository;
    private AccusationController accusationController;

    @Autowired
    private TurnService (TurnRepository turnRepository, AccusationController accusationController){
        this.turnRepository=turnRepository;
        this.accusationController=accusationController;
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
        accusationController.makeAccusation();
        turn.setPhase(Phase.FINAL);
        return save(turn);
    }

    public Turn makeFinalAcusation(Turn turn) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.FINAL){
            throw new WrongPhaseException();
        }
        accusationController.makeFinalAcusation();
        turn.setPhase(Phase.FINISHED);
        return save(turn);
    }

    @Transactional
    public Turn save(Turn turn){
        return turnRepository.save(turn);
    }
}
