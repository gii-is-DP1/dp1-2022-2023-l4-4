package org.springframework.cluedo.celd;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.enumerates.CeldType;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CeldServiceTest {

    @Mock
    protected CeldRepository repo;
    
    protected CeldService service;

    List<Celd> celds = new ArrayList<>();

    @BeforeEach
    public void config(){
        service =new CeldService(repo);
        
        Celd c1 = new Celd();
        Celd c2 = new Celd();
        Celd c3 = new Celd();
        Celd c4 = new Celd();

        c1.setId(1);
        c1.setPosition(1);
        c1.setCeldType(CeldType.CENTER);

        c2.setId(2);
        c2.setPosition(2);
        c2.setCeldType(CeldType.CORRIDOR);

        c3.setId(3);
        c3.setPosition(3);
        c3.setCeldType(CeldType.CORRIDOR);

        c4.setId(4);
        c4.setPosition(4);
        c4.setCeldType(CeldType.KITCHEN);

        c1.setConnectedCelds(List.of(c2,c3));
        c2.setConnectedCelds(List.of(c1,c4));
        c3.setConnectedCelds(List.of(c1,c4));
        c4.setConnectedCelds(List.of(c2,c3));

        celds.add(c1);
        celds.add(c2);
        celds.add(c3);
        celds.add(c4);



    }

    @Test
    public void testGetAllCelds(){
        when(repo.findAll()).thenReturn(celds);
        List<Celd> celdss = service.getAllCelds();
        assertNotNull(celdss);
        assertFalse(celdss.isEmpty());
    }

    @Test
    public void testGetAllPairs(){
        when(repo.findAll()).thenReturn(celds);
        List<List<Celd>> pairs = service.getAllPairs();
        assertNotNull(pairs);
        assertTrue(pairs.size()==8);   
    }

    @Test
    public void testGetCenter(){
        when(repo.findCenter()).thenReturn(celds.get(0));
        Celd c = service.getCenter();
        assertNotNull(c);
        assertEquals(c.getCeldType(), CeldType.CENTER);
    }

    /*@Test
    public void testGetById() throws DataNotFound{
        when(repo.findById(1).get()).thenReturn(celds.get(0));
        Celd c = service.getById(1);
        assertNotNull(c);
        assertEquals(c, celds.get(0));
    }

    @Test
    public void testShouldNotGetById() throws DataNotFound{
        when(repo.findById(1).get()).thenReturn(celds.get(0));
        Celd c = service.getById(10);
        assertThrows(DataNotFound.class());
    } */

    @Test
    public void testGetByCeldType(){
        when(repo.findByCeldType(CeldType.KITCHEN)).thenReturn(celds.get(3));
        Celd c = service.getByCeldType(CeldType.KITCHEN);
        assertNotNull(c);
        assertEquals(c, celds.get(3));
    }

    @Test
    public void testGetAllPossibleMovements(){

    }
}