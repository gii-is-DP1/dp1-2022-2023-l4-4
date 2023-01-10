package org.springframework.cluedo.turn;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.celd.Celd;
import org.springframework.cluedo.celd.CeldService;
import org.springframework.cluedo.enumerates.Phase;
import org.springframework.cluedo.exceptions.CorruptGame;
import org.springframework.cluedo.exceptions.WrongPhaseException;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {
    
    private TurnRepository turnRepository;
    private CeldService celdService;

    @Autowired
    public TurnService (TurnRepository turnRepository, CeldService celdService){
        this.turnRepository=turnRepository;
        this.celdService = celdService;
    }

    public Turn createTurn(UserGame userGame,Integer round){
        Turn turn=new Turn();
        turn.setUserGame(userGame);
        turn.setRound(round);
        Optional<Turn> previousTurn = turnRepository.getTurn(userGame.getId(),round-1);
        if(previousTurn.isPresent()){
            
            turn.setInitialCeld(previousTurn.get().getFinalCeld());
        } else{
            turn.setInitialCeld(celdService.getCenter());
        } 
        turn.setPhase(Phase.DICE);
        saveTurn(turn);
        return turn;
    }
   
    public Optional<Turn> getTurn(UserGame userGame,Integer round){
        return turnRepository.getTurn(userGame.getId(),round);
    }

    public Optional<Turn> getActualTurn(Game game){
        return getTurn(game.getActualPlayer(),game.getRound());
    }

    public Turn throwDice(Game game) throws WrongPhaseException{
        Optional<Turn> nrTurn=getActualTurn(game);
        if(!nrTurn.isPresent() || nrTurn.get().getPhase()!=Phase.DICE){
                throw new WrongPhaseException();
        }
        Turn turn = nrTurn.get();
        Integer result = ThreadLocalRandom.current().nextInt(6)+1;
        result +=ThreadLocalRandom.current().nextInt(6)+1;
        turn.setDiceResult(result);
        turn.setPhase(Phase.MOVEMENT);
        return saveTurn(turn);
    } 

    public Set<Celd> whereCanIMove(Game game) throws CorruptGame{
        Optional<Turn> nrTurn=getTurn(game.getActualPlayer(), game.getRound());
            if(nrTurn.isPresent()){
                Turn turn=nrTurn.get();
                return celdService.getAllPossibleMovements(turn.getDiceResult(), turn.getInitialCeld());
            }else{ 
                throw new CorruptGame();
            }
    }
 
    public Turn moveCharacter(Turn turn,Celd finalCeld) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.MOVEMENT){
            throw new WrongPhaseException();
        }
        //celdController.movement()
        turn.setFinalCeld(finalCeld);
        turn.setPhase(Phase.ACCUSATION);
        return (turn);
    }

    public Turn makeAccusation(Game game) throws WrongPhaseException{
        Optional<Turn> nrTurn=getTurn(game.getActualPlayer(), game.getRound());
        if(!nrTurn.isPresent() || nrTurn.get().getPhase()!=Phase.ACCUSATION){
                throw new WrongPhaseException();
        }
        Turn turn = nrTurn.get();
        turn.setPhase(Phase.FINAL);
        return saveTurn(turn);
    }

    public Turn makeFinalDecision(Turn turn) throws WrongPhaseException{
        if(turn.getPhase()!=Phase.FINAL){
            throw new WrongPhaseException();
        }
        turn.setPhase(Phase.FINISHED);
        return saveTurn(turn);
    }

    @Transactional
    public Turn saveTurn(Turn turn){
        return turnRepository.save(turn);
    } 
}
