package org.springframework.cluedo.achievement;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.accusation.AccusationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/achievements")
public class AchievementController {
    private AchievementService achievementService;
    private final String ACHIEVEMENTS_LISTING = "achievements/achievementsListing";
    private final String CREATE_ACHIEVEMENT = "achievements/createNewAchievement";

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    // H?? - Listar todos los logros
    @GetMapping()
    public ModelAndView getAllAchievements(){
        List<Achievement> achievements = achievementService.getAllAchievements();
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_LISTING);
        result.addObject("achievements", achievements);
        return result;
    }

    //H24 - Creacion de logos
    @GetMapping("/new")
    public ModelAndView formNewAchievement(){
        Achievement achievement = new Achievement();
        ModelAndView result = new ModelAndView();
        result.addObject("achievement", achievement);
        return result;
    }
    @Transactional

    
    @PostMapping("/new")
    public ModelAndView saveNewAchievement(@Valid Achievement achievement, BindingResult br) {
        if(br.hasErrors()) {
            System.out.println(br.getAllErrors().toString());
            return new ModelAndView(CREATE_ACHIEVEMENT, br.getModel());
        } else {
            achievementService.saveAchievement(achievement);
            ModelAndView result = new ModelAndView(CREATE_ACHIEVEMENT);
            result.addObject("achievement", achievement);
            return result;
        }
    }
}
