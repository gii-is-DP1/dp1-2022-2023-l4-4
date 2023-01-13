package org.springframework.cluedo.statistics;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserStatisticsController {

    private final String GLOBAL_STATISTICS = "users/globalStatistics";
	private final String STATISTICS = "users/statistics";
    
    private GameService gameService;
    private UserStatisticsService userStatisticsService;

    @Autowired
    public UserStatisticsController(GameService gameService, UserStatisticsService statisticsService){
        this.gameService=gameService;
		this.userStatisticsService=statisticsService;
    }

    @Transactional(readOnly=true)
	@GetMapping("/stats")
	public ModelAndView getMyStatistics(){
		ModelAndView result = new ModelAndView(STATISTICS);
		UserStatistics statistics = userStatisticsService.getMyStatistics();
		result.addObject("stats", statistics);
		return result;
	}

	@Transactional(readOnly=true)
	@GetMapping("/global")
	public ModelAndView getGlobalStatistics(){
		ModelAndView result = new ModelAndView(GLOBAL_STATISTICS);
		List<UserStatistics> allStats = userStatisticsService.getAllStatistics();
		List<Game> allGames = gameService.getAllFinishedGames();
		GlobalStatistics stats = new GlobalStatistics(allGames, allStats);
		result.addObject("stats", stats);
		return result;
	}
}
