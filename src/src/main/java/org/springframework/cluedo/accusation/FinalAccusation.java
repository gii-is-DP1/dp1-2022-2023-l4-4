package org.springframework.cluedo.accusation;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
    
import org.springframework.cluedo.turn.Turn;
    
import lombok.Getter;
import lombok.Setter;
    
@Entity
@Setter
@Getter
@Table(name="final_accusations")
public class FinalAccusation extends BaseAccusation{
    
    @OneToOne
    @JoinColumn(name = "turn")
    @NotNull
    private Turn turn;
    
    private boolean isCorrect;
    


}
    
