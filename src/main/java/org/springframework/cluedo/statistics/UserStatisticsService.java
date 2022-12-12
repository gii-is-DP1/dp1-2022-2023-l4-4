package org.springframework.cluedo.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatisticsService {
    
    @Autowired
    private UserStatisticsRepository repo;

    @Transactional
    public void save(UserStatistics statistics) throws DataAccessException{
        repo.save(statistics);
    }

    @Transactional(readOnly=true)
    public List<UserStatistics> getAllStatistics(){
        return repo.findAll();
    }
}
