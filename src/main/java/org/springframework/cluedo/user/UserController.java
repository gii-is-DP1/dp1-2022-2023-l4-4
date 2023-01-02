/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cluedo.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.achievement.Achievement;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.cluedo.game.Game;
import org.springframework.cluedo.game.GameService;
import org.springframework.cluedo.statistics.GlobalStatistics;
import org.springframework.cluedo.statistics.UserStatistics;
import org.springframework.cluedo.statistics.UserStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller


public class UserController {

	private static final String VIEWS_USER_LIST = "users/userList";
	private final String ACHIEVEMENTS_LISTING = "achievements/myAchievements";
  	private static final String VIEWS_USER_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";
	private static final String UPDATE_OTHER_USER_FORM_ADMIN = "users/updateOtherUserForm";
	private static final String GLOBAL_STATISTICS = "users/globalStatistics";
	private final String STATISTICS = "users/statistics";
	private static final String ADD_FRIENDS_FORM = "users/addFriend";
	private static final String DELETE_FRIENDS_FORM = "users/deleteFriend";

	private UserService userService;

	private UserStatisticsService statisticsService;

	private GameService gameService;

	@Autowired
    public UserController(UserService userService,UserStatisticsService statisticsService, GameService gameService) {
        this.userService = userService;
		this.statisticsService=statisticsService;
		this.gameService= gameService;
    }

	@Transactional(readOnly=true)
	@GetMapping(value ="/users")
    public ModelAndView showUserList() {
        ModelAndView mav = new ModelAndView(VIEWS_USER_LIST);
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }
	@Transactional(readOnly=true)
	@GetMapping(value="/users/{userId}")
	public ModelAndView showUser(@PathVariable("userId") int userId) throws DataNotFound{
		ModelAndView mav = new ModelAndView("users/userDetails");
		Optional<User> nrUser = userService.findUserById(userId);
		if(nrUser.isPresent()){
			mav.addObject("user", nrUser.get());
		return mav;
		}
		throw new DataNotFound();
	}
	@Transactional(readOnly=true)
	@GetMapping(value="/users/friends")
	public ModelAndView redirectFriends() {
		ModelAndView mav = new ModelAndView("redirect:/users/"+userService.getLoggedUser().get().getId()+"/friends");
		return mav;
	}
	
	@Transactional(readOnly=true)
	@GetMapping(value="/users/{userId}/friends/delete")
	public ModelAndView initDeleteFriendForm() {
		ModelAndView mav = new ModelAndView(DELETE_FRIENDS_FORM);
		mav.addObject("UsernameForm", new UsernameForm());
		return mav;
	}

	@Transactional
	@PostMapping(value = "/users/{userId}/friends/delete")
	public ModelAndView processDeleteFriendForm(@Valid UsernameForm username, BindingResult br) {
		ModelAndView result= new ModelAndView(DELETE_FRIENDS_FORM);
		result.addObject("UsernameForm", new UsernameForm());
		if(br.hasErrors()){
			result.addObject("message","Username must not be empty. Please try again.");
		}else{
			Optional<User> userByUsername = this.userService.findUserByUsername(username.getUsername());
			if (!userByUsername.isPresent()) {
				result.addObject("message", "Username not found. Please try again");
			}
			else {
				User loggedUser = this.userService.getLoggedUser().get();
				User friendToDelete = userByUsername.get();
				if(!loggedUser.getFriends().contains(friendToDelete)){
					result.addObject("message", "This user is not on your list of friends. Please try again");
				} else {
					userService.deleteFriend(friendToDelete);
					result= new ModelAndView("redirect:/users/"+loggedUser.getId()+"/friends");
					result.addObject("user", loggedUser);
				}
			}
		}
		return result;	
	}

	@Transactional(readOnly=true)
	@GetMapping(value="/users/{userId}/friends/add")
	public ModelAndView initAddFriendForm() {
		ModelAndView mav = new ModelAndView("users/addFriend");
		mav.addObject("TagForm", new TagForm());
		return mav;
	}

	@Transactional
	@PostMapping(value = "/users/{userId}/friends/add")
	public ModelAndView processAddFriendForm(@Valid TagForm tagForm, BindingResult br) {
		ModelAndView result= new ModelAndView(ADD_FRIENDS_FORM);
		result.addObject("TagForm", new TagForm());
		if(br.hasErrors()){
			result.addObject("message","Tag must not be empty. Please try again");
		}else{
			User userByTag = this.userService.findUserByTag(tagForm.getTag());
			if (userByTag==null) {
				result.addObject("message","User not found. Please try again");	
			}
			else {
				User loggedUser = this.userService.getLoggedUser().get();
				userService.addFriend(userByTag);
				result= new ModelAndView("redirect:/users/"+loggedUser.getId()+"/friends");
			}
		}
		return result;
	} 

	@GetMapping(value="/users/{userId}/friends")
	public ModelAndView showUserFriends(@PathVariable("userId") int userId) throws DataNotFound{
		ModelAndView mav = new ModelAndView("users/userFriends");
		List<User> nrUser = userService.findUserFriends(userId);
		if(nrUser.size()>=0){
			mav.addObject("user", nrUser);
			return mav;
		}
		throw new DataNotFound();
	}

	@GetMapping(value="/users/{userId}/edit")
	public String initUpdateUserForm(@PathVariable("userId") int userId,Model model) throws DataNotFound{
		Optional<User> nrUser = this.userService.findUserById(userId);
		if(nrUser.isPresent()){
			model.addAttribute(nrUser.get());
			return UPDATE_OTHER_USER_FORM_ADMIN;
		}
		throw new DataNotFound();
	}
	@Transactional(readOnly=true)
	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
	}
  
	@Transactional
	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}
		else {
			user.setAuthority("user");
			user.setEnabled(1);
			user.setFriends(new ArrayList<>());
			user.setAchievements(new ArrayList<>());
			user.setTag(generarTag());
			this.userService.saveUser(user);
			UserStatistics statistics = new UserStatistics();
			statistics.setUser(user);
			this.statisticsService.save(statistics);
			return "redirect:/";
		}
	}
	@Transactional
 	@PostMapping(value= "/users/{userId}/edit")
	public String processUpdateForm(@Valid User user, BindingResult result,@PathVariable("userId") int userId){
		User userToChange = userService.findUserById(userId).get();
		if(result.hasErrors()){
			return UPDATE_OTHER_USER_FORM_ADMIN;
		}else{
			userToChange.setUsername(user.getUsername());
			userToChange.setPassword(user.getPassword());
			userToChange.setEmail(user.getEmail());
			userToChange.setImageurl(user.getImageurl());
			this.userService.saveUser(userToChange);
			return "redirect:/users/{userId}";
		}
	} 

	@Transactional(readOnly=true)
	@GetMapping(value = "/users/{userId}/delete")
	public String deleteUser(@PathVariable("userId") int userId){
		this.userService.deleteUser(userId);
		return "redirect:/users/";
	}
  
	@Transactional(readOnly=true)
	@GetMapping(value = "/profile")
	public ModelAndView showProfile(Map<String, Object> model) throws DataNotFound{
		Optional<User> nrUser = userService.getLoggedUser();
		ModelAndView mav = new ModelAndView("users/profile");
		if(nrUser.isPresent()){
			mav.addObject("user", nrUser.get());
		return mav;
		}
		throw new DataNotFound();
	}	
	@Transactional(readOnly=true)
	@GetMapping(value="/profile/edit")
	public String initUpdateUserProfileForm(Model model) throws DataNotFound{
		Optional<User> nrUser = userService.getLoggedUser();
		if(nrUser.isPresent()){
			User user = nrUser.get();
			user.setPassword("");
			model.addAttribute(user);
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}
		throw new DataNotFound();
	}
	@Transactional
	@PostMapping(value= "/profile/edit")
	public String processUpdateFormProfile(@Valid User user, BindingResult br){
		Optional<User> nrUser = userService.getLoggedUser();
		if(br.hasErrors()){
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}else{
			User userToChange = nrUser.get();
			userToChange.setUsername(user.getUsername());
			userToChange.setPassword(user.getPassword());
			userToChange.setEmail(user.getEmail());
			userToChange.setImageurl(user.getImageurl());
			this.userService.saveUser(userToChange);
			return "redirect:/profile";
		}
	} 
	@Transactional(readOnly=true)
	@GetMapping("/myAchievements")
	public ModelAndView getAllMyAchievements(){
		ModelAndView result= new ModelAndView(ACHIEVEMENTS_LISTING);
		List<Achievement> achievements = userService.findAllMyAchievements();
		result.addObject("achievements", achievements);
		return result; 
	}
	@Transactional(readOnly=true)
	@GetMapping("/stats")
	public ModelAndView getMyStatistics(){
		ModelAndView result = new ModelAndView(STATISTICS);
		UserStatistics statistics = userService.getMyStatistics();
		result.addObject("stats", statistics);
		return result;
	}
	@Transactional(readOnly=true)
	@GetMapping("/global")
	public ModelAndView getGlobalStatistics(){
		ModelAndView result = new ModelAndView(GLOBAL_STATISTICS);
		List<UserStatistics> allStats = statisticsService.getAllStatistics();
		List<Game> allGames = gameService.getAllFinishedGames();
		GlobalStatistics stats = new GlobalStatistics(allGames, allStats);
		result.addObject("stats", stats);
		return result;
	}


	public static String generarTag(){
		//La variable palabra almacena el resultado final 
			String palabra = "#"; 
		//La variable caracteres es un número aleatorio entre 2 y 20 que define la 
		//longitud de la palabra. 
			int caracteres = 4; 
		//Con un bucle for, que recorreremos las veces que tengamos almacenadas en la 
		//variable caracteres, que será como mínimo 2, iremos concatenando los 
		//caracteres aleatorios. 
				 for (int i=0; i<caracteres; i++){ 
		//Para generar caracteres aleatorios hay que recurrir al valor numérico de estos 
		//caracteres en el alfabeto Ascii. En este programa vamos a generar palabras con 
		//letras minúsculas, que se encuentran en el rango 65-90. El método floor 
		//devuelve el máximo entero. 
				 int codigoAscii = (int)Math.floor(Math.random()*(90 -
				 65)+65); 
		//para pasar el código a carácter basta con hacer un cast a char 
				 palabra = palabra + (char)codigoAscii; 
				 } 
				 String numero = (ThreadLocalRandom.current().nextInt(8)+1)+"";
				 return palabra + numero+numero+numero+numero ; 
			 } 


}
