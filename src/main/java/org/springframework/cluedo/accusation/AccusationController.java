package org.springframework.cluedo.accusation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccusationController {
    private AccusationService accusationService;

    @Autowired
    public AccusationController(AccusationService accusationService) {
        this.accusationService = accusationService;
    }

    public String makeAccusation() {
        return null;
    }

    public Accusation makeFinalAcusation(){
        return null;
    }
}
