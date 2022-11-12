package org.springframework.cluedo.celd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CeldRepository extends CrudRepository<Celd,Integer>{
    List<Celd> findAll();
}
