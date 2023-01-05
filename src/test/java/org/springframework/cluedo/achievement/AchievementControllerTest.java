package org.springframework.cluedo.achievement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cluedo.configuration.SecurityConfiguration;
import org.springframework.cluedo.enumerates.Badge;
import org.springframework.cluedo.enumerates.Metric;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.mvc.method.annotation.RedirectAttributesMethodArgumentResolver;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers=AchievementController.class,
excludeFilters=@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes=WebSecurityConfigurer.class),
excludeAutoConfiguration=SecurityConfiguration.class)
public class AchievementControllerTest {
    
    @MockBean
    protected AchievementService achievementService;

    @Autowired
    protected MockMvc mockMvc;

    private Achievement achievement1;
    private Achievement achievement2;
    private List<Achievement> achievements;

    @BeforeEach
    public void config(){
        achievements = new ArrayList<>();

        achievement1 = new Achievement();
        achievement1.setId(1);
        achievement1.setName("Test 1");
        achievement1.setDescription("Test example 1");
        achievement1.setGoal(100);
        achievement1.setMetric(Metric.EXPERIENCE);
        achievement1.setBadgeType(Badge.BRONZE);
        achievement1.setXp(100);
        achievements.add(achievement1);

        achievement2 = new Achievement();
        achievement2.setId(2);
        achievement2.setName("Test 2");
        achievement2.setDescription("Test example 2");
        achievement2.setGoal(500);
        achievement2.setMetric(Metric.EXPERIENCE);
        achievement2.setBadgeType(Badge.SILVER);
        achievement2.setXp(100);
        achievements.add(achievement2);
    }   

    @WithMockUser
    @Test
    public void testGetAllAchievements() throws Exception{
        when(achievementService.getAllAchievements()).thenReturn(achievements);
        mockMvc.perform(get("/achievements")).
                andExpect(status().isOk()).
                andExpect(view().name("achievements/achievementsListing")).
                andExpect(model().attributeExists("achievements"));
    }

    @WithMockUser
    @Test
    public void testGetFormCreateAchievement() throws Exception{
        mockMvc.perform(get("/achievements/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("achievements/createEditNewAchievement"))
        .andExpect(model().attributeExists("achievement","badge","metric"));
    }

    @WithMockUser
    @Test
    public void testPostFormCreateAchievementSuccesfull() throws Exception{
        mockMvc.perform(post("/achievements/new")
            .with(csrf())
            .param("id", "1")
            .param("name", "Test 1")
            .param("description", "Test Example 1")
            .param("goal", "100")
            .param("metric", "EXPERIENCE")
            .param("badgeType", "BRONZE")
            .param("xp", "100"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/achievements"))
        .andExpect(MockMvcResultMatchers.flash().attributeExists("message"));
    }

    @WithMockUser
    @Test
    public void testPostFormCreateAchievementFailed() throws Exception{
        mockMvc.perform(post("/achievements/new")
            .with(csrf())
            .param("description", "Test Example 1")
            .param("goal", "100")
            .param("metric", "EXPERIENCE")
            .param("badgeType", "BRONZE")
            .param("xp", "100"))
        .andExpect(view().name("achievements/createEditNewAchievement"))
        .andExpect(model().attributeExists("achievement"));
    }

    @WithMockUser
    @Test
    public void testGetFormEditAchievement() throws Exception{
        when(achievementService.getAchievementById(any(Integer.class))).thenReturn(achievement1);
        mockMvc.perform(get("/achievements/{id}/edit",achievement1.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("achievements/createEditNewAchievement"))
        .andExpect(model().attributeExists("achievement","badge","metric"));
    }

    @WithMockUser
    @Test
    public void testPostFormEditAchievementSuccessfull() throws Exception{
        when(achievementService.getAchievementById(any(Integer.class))).thenReturn(achievement1);
        mockMvc.perform(post("/achievements/{id}/edit",achievement1.getId())
            .with(csrf())
            .param("id", "1")
            .param("name", "Test 11")
            .param("description", "Test Example 11")
            .param("goal", "1000")
            .param("metric", "EXPERIENCE")
            .param("badgeType", "SILVER")
            .param("xp", "1000"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/achievements"));
    }

    @WithMockUser
    @Test
    public void testPostFormEditAchievementFailed() throws Exception{
        when(achievementService.getAchievementById(any(Integer.class))).thenReturn(achievement1);
        mockMvc.perform(post("/achievements/{id}/edit",achievement1.getId())
            .with(csrf())
            .param("description", "Test Example 11")
            .param("goal", "1000")
            .param("metric", "EXPERIENCE")
            .param("badgeType", "SILVER")
            .param("xp", "1000"))
        .andExpect(view().name("achievements/createEditNewAchievement"))
        .andExpect(model().attributeExists("achievement"));
    }

    @WithMockUser
    @Test
    public void testGetAllMyAchievements() throws Exception{
        when(achievementService.findAllMyAchievements()).thenReturn(List.of(achievement1));
        mockMvc.perform(get("/myAchievements"))
        .andExpect(status().isOk())
        .andExpect(view().name("achievements/achievementsListing"))
        .andExpect(model().attributeExists("achievements"));
    }

}
