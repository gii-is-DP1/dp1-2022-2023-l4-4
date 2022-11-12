package org.springframework.cluedo.celd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CeldService {
    @Autowired
    CeldRepository celdRepository;

    public CeldService(CeldRepository cRepository) {
        this.celdRepository=cRepository;
    }

    public List<Celd> getAllCelds() {
        return celdRepository.findAll();
    }

    public List<List<Celd>> getAllConnections() {
        return celdRepository.findAllConnections();
    }
}
