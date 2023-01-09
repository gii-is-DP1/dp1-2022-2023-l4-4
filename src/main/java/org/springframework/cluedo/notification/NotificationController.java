package org.springframework.cluedo.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.user.User;
import org.springframework.cluedo.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/notifications")
public class NotificationController {
    

    private UserService userService;
    private NotificationService notificationService;

    @Autowired
    public NotificationController(UserService userService, NotificationService notificationService){
        this.userService=userService;
        this.notificationService=notificationService;
    }

    @Transactional(readOnly=true)
	@GetMapping(value="/redirect")
	public ModelAndView redirectFriends() {
		ModelAndView mav = new ModelAndView("redirect:/notifications/"+userService.getLoggedUser().get().getId());
		return mav;
	}

    @GetMapping("/{userId}")
    public ModelAndView showNotifications(@PathVariable("userId") int userId)throws DataNotFound {

        ModelAndView mav = new ModelAndView("notifications/myNotifications");
        List<Notification> nrNoti = notificationService.getNotificationsByReceiverId(userId);
        if (nrNoti.size() >= 0){
            mav.addObject("notification",nrNoti);
            return mav;
        }
        throw new DataNotFound();

    }

    @GetMapping("{lobbyId}/invite")
    public ModelAndView showFriendsToInvite(@PathVariable("lobbyId")int lobbyId)throws DataNotFound{
        
        ModelAndView mav = new ModelAndView("notifications/inviteFriends");
        
		List<User> nrUser = userService.findUserFriends(userService.getLoggedUser().get().getId());
		if(nrUser.size()>=0){
            mav.addObject("lobbyId", lobbyId);
			mav.addObject("user", nrUser);
			return mav;
		}
		throw new DataNotFound();
	}

    @GetMapping("{lobbyId}/invite/{userId}")
    public String inviteFriend(@PathVariable("lobbyId")int lobbyId, @PathVariable("userId")int userId){
    User user = userService.getLoggedUser().get();
    LocalDateTime localDate = LocalDateTime.now();//For reference
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String formattedString = localDate.format(formatter);

    Notification noti = new Notification();
    noti.setLink(lobbyId);
    noti.setSender(user);
    noti.setReceiver(userService.findUserById(userId).get());
    noti.setText(user.getUsername()+" te ha invitado a una partida    ");
    noti.setTimestamp(formattedString);
    notificationService.save(noti);
    
	return "redirect:/games/"+lobbyId+"/lobby";
	}
}
