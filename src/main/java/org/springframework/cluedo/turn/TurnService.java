package org.springframework.cluedo.turn;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.celd.CeldRepository;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {
    
    private TurnRepository turnRepository;
    private CeldRepository celdRepository;

    @Autowired
    public TurnService (TurnRepository turnRepository, CeldRepository celdRepository){
        this.turnRepository=turnRepository;
        this.celdRepository = celdRepository;
    }

    public Turn createTurn(UserGame userGame,Integer round){
        Turn turn=new Turn();
        turn.setUserGame(userGame);
        turn.setRound(round);
        Optional<Turn> previousTurn = turnRepository.getTurn(userGame.getId(),round-1);
        if(previousTurn.isPresent()){
            turn.setInitialCeld(previousTurn.get().getFinalCeld());
        } else{
            turn.setInitialCeld(celdRepository.findCenter());
        } 
        turn.setPhase(Phase.DICE);
        return turn;
    }
   
    public Optional<Turn> getTurn(UserGame userGame,Integer round){
        return turnRepository.getTurn(userGame.getId(),round);
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
