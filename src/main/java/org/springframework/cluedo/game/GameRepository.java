package org.springframework.cluedo.game;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game,Integer>{
     List<Game> findAll() throws DataAccessException;
}
