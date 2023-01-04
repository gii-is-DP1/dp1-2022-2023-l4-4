package org.springframework.cluedo.achievement;

import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AchievementController {
    
    private AchievementService achievementService;
    
    private final String ACHIEVEMENTS_LISTING = "achievements/achievementsListing";
    private final String CREATE_EDIT_ACHIEVEMENT = "achievements/createEditNewAchievement";

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
       
    }

    @ModelAttribute("metric")
    public List<Metric> getMetrics(){
        List<Metric> result = new ArrayList<>();
        for(Metric metric : Metric.values()){
            result.add(metric);
        }
        return result;
    }
    @ModelAttribute("badge")
    public List<Badge> getBadges(){
        List<Badge> result = new ArrayList<>();
        for(Badge metric : Badge.values()){
            result.add(metric);
        }
        return result;
    }

    @Transactional(readOnly=true)
    @GetMapping("/achievements")
    public ModelAndView getAllAchievements(){
        List<Achievement> achievements = achievementService.getAllAchievements();
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_LISTING);
        result.addObject("achievements", achievements);
        result.addObject("canCreateAndEdit",true);
        return result;
    }

    @Transactional(readOnly=true)
    @GetMapping("/achievements/new")
    public ModelAndView createAchievement(){
        Achievement achievement = new Achievement();
        ModelAndView result = new ModelAndView(CREATE_EDIT_ACHIEVEMENT);
        result.addObject("achievement", achievement);
        return result;
    }
    

    @Transactional
    @PostMapping("/achievements/new")
    public ModelAndView saveCreatedAchievement(@Valid Achievement achievement, BindingResult br,RedirectAttributes attributes) {
        if(br.hasErrors()) {
            return new ModelAndView(CREATE_EDIT_ACHIEVEMENT, br.getModel());
        } else {
            String imagen = "/resources/images/" + achievement.getBadgeType().toString().toLowerCase() + ".jpg";
            achievement.setImageUrl(imagen);
            achievementService.saveAchievement(achievement);
            ModelAndView result = new ModelAndView("redirect:/achievements");
            attributes.addFlashAttribute("message", "The achievement was created successfully");
            return result;
        }
    }

    @Transactional(readOnly=true)
    @GetMapping("/achievements/{id}/edit")
    public ModelAndView editAchievement(@PathVariable("id") Integer id) {
        Achievement achievement = achievementService.getAchievementById(id);
        ModelAndView result = new ModelAndView(CREATE_EDIT_ACHIEVEMENT);
        if(achievement != null){
            result.addObject("achievement", achievement);
        } else {
            result=getAllAchievements();
            result.addObject("message", "The achievement with id " + id + "doesn't exist");
        }
        return result;
    }
    
    @Transactional
    @PostMapping("/achievements/{id}/edit")
    public ModelAndView saveEditedAchievement(@Valid Achievement achievement, BindingResult br, RedirectAttributes attributes){
        if(br.hasErrors()){
            return new ModelAndView(CREATE_EDIT_ACHIEVEMENT,br.getModel());
        } else {
            ModelAndView result = new ModelAndView("redirect:/achievements");
            Achievement achievementToEdit = achievementService.getAchievementById(achievement.getId());
            BeanUtils.copyProperties(achievement, achievementToEdit, "id","image_url");
            achievementToEdit.setImageUrl("/resources/images/" + achievement.getBadgeType().toString().toLowerCase() + ".jpg");
            achievementService.saveAchievement(achievementToEdit);
            attributes.addFlashAttribute("message", "The achievement was edited successfully");
            return result;
        }
    }

    @Transactional(readOnly=true)
	@GetMapping("/myAchievements")
	public ModelAndView getAllMyAchievements(){
		ModelAndView result= new ModelAndView(ACHIEVEMENTS_LISTING);
		List<Achievement> achievements = achievementService.findAllMyAchievements();
		result.addObject("achievements", achievements);
		return result; 
	}
}
