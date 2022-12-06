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
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller


public class UserController {

	private static final String VIEWS_USER_LIST = "users/userList";
  
  	private static final String VIEWS_USER_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";

	
	@Autowired
	private UserService userService;
	

	@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

	
	@GetMapping(value ="/users")
    public ModelAndView showUserList() {
        ModelAndView mav = new ModelAndView(VIEWS_USER_LIST);
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

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
	@GetMapping(value="/users/{userId}/friends")
	public ModelAndView showUserFriends(@PathVariable("userId") int userId) throws DataNotFound{
		ModelAndView mav = new ModelAndView("users/userFriends");
		List<User> nrUser = userService.findUserFriends(userId);
		if(nrUser.size()>0){
			mav.addObject("user", nrUser);	
		return mav;
		}
		if(nrUser.size()==0){
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
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}
		throw new DataNotFound();
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USER_CREATE_OR_UPDATE_FORM;
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

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}
		else {
			List emptyFriendList = new ArrayList();
			user.setFriends(emptyFriendList);
			user.setTag(generarTag());
			this.userService.saveUser(user);
			return "redirect:/";
		}
	}

 	@PostMapping(value= "/users/{userId}/edit")
	public String processUpdateForm(@Valid User user, BindingResult result,@PathVariable("userId") int userId){
		
		if(result.hasErrors()){
			return VIEWS_USER_CREATE_OR_UPDATE_FORM;
		}else{
			user.setId(userId);
			this.userService.saveUser(user);
			return "redirect:/users/{userId}";
		}
	} 

	@GetMapping(value = "/users/{userId}/delete")
	public String deleteUser(@PathVariable("userId") int userId){
		this.userService.deleteUser(userId);
		return "redirect:/users/";
	}

}
