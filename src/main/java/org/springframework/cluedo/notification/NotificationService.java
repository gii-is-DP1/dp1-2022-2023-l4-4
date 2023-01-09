package org.springframework.cluedo.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private NotificationRepository notificationRepository;

    @Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;  
		
	}

    public List<Notification> getNotificationsByReceiverId(Integer id) {
        return notificationRepository.findNotificationsByReceiverId(id);
    }

    public Notification save(Notification noti){
        return notificationRepository.save(noti);
    }



}