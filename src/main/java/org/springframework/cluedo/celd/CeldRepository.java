package org.springframework.cluedo.celd;

import java.util.List;

import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CeldRepository extends CrudRepository<Celd,Integer>{
    List<Celd> findAll();

    @Query("SELECT c FROM Celd c WHERE c.celdType LIKE org.springframework.cluedo.enumerates.CeldType.CENTER")
    Celd findCenter();

    @Query("SELECT c FROM Celd c WHERE c.celdType = :celdType")
    Celd findByCeldType(@Param("celdType") CeldType celdType);
}
