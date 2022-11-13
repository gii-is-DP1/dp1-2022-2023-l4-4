package org.springframework.cluedo.turn;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {
    
    private TurnRepository turnRepository;

    @Autowired
    public TurnService (TurnRepository turnRepository){
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

    public Turn moveCharacter(Turn turn,Celd finalCeld) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.MOVEMENT){
            throw new WrongPhaseException();
        }
        //celdController.movement()
        turn.setFinalCeld(finalCeld);
        turn.setPhase(Phase.ACUSATION);
        return (turn);
    }

    public Turn makeAccusation(Turn turn) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.ACUSATION){
            throw new WrongPhaseException();
        }
        //accusationController.makeAccusation();
        turn.setPhase(Phase.FINAL);
        return save(turn);
    }

    public Turn makeFinalDecision(Turn turn,boolean finalAccusation) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.FINAL){
            throw new WrongPhaseException();
        }
       /* if(finalAccusation){
            accusationController.makeFinalAcusation();
        }*/ 
        turn.setPhase(Phase.FINISHED);
        return save(turn);
    }

    @Transactional
    public Turn save(Turn turn){
        return turnRepository.save(turn);
    }
}
