package org.springframework.cluedo.celd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/celd")
public class CeldController {
    @Autowired
    CeldService celdService;

    @GetMapping("/all")
    public void getAllCelds() {
        List<Celd> celds = celdService.getAllCelds();
        for (Celd celd:celds) {
            System.out.println(celd.getId());
        }
    }
}
