package org.springframework.cluedo.celd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CeldRepository extends CrudRepository<Celd,Integer>{
    List<Celd> findAll();

    @Query("SELECT c FROM Celd c WHERE c.celdType LIKE CENTER")
    Celd findCenter();
}
