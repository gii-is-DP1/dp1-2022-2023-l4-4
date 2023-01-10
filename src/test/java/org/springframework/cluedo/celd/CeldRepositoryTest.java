package org.springframework.cluedo.celd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.CeldType;

@DataJpaTest
public class CeldRepositoryTest {

    @Autowired
    CeldRepository repo;

    @Test
    public void testFindAll(){
        List<Celd> celds = repo.findAll();
        assertNotNull(celds);
        assertFalse(celds.isEmpty());
    }

    @Test
    public void testFindByCeldType(){
        CeldType type = CeldType.CENTER;
        Celd celd = repo.findByCeldType(type);
        assertNotNull(celd);
        assertEquals(celd.getCeldType(), type);
    }

}