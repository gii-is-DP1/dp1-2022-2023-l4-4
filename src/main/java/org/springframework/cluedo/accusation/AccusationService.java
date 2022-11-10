package org.springframework.cluedo.accusation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccusationService {
    private AccusationRepository accusationRepository;

    @Autowired
    public AccusationService(AccusationRepository accusationRepository) {
        this.accusationRepository = accusationRepository;
    }
    
    public Accusation saveAccusation(Accusation accusation) {
        return accusationRepository.save(accusation);
    }
}
