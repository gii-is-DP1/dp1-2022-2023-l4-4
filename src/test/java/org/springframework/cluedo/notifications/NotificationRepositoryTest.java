package org.springframework.cluedo.notifications;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.notification.Notification;
import org.springframework.cluedo.notification.NotificationRepository;
import org.springframework.cluedo.user.User;

@DataJpaTest
public class NotificationRepositoryTest {
    
    @Autowired 
    NotificationRepository notificationRepository;
    
    Notification notification;
    @BeforeEach
    public void config(){
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        notification = new Notification();
        notification.setId(1);
        notification.setSender(user1);
        notification.setReceiver(user2);
    }
    @Test
    public void testFindNotificationsByReceiverId(){

    }

    @Test
    public void testDeleteNotificactionsByReceiverId(){
        List<Notification> notificationBeforeDelete= notificationRepository.findNotificationsByReceiverId(2);
        notificationRepository.deleteNotificactionsByReceiverId(2);
        List<Notification> notificationsAfterDelete = notificationRepository.findNotificationsByReceiverId(2); 
        assertTrue(!notificationBeforeDelete.equals(notificationsAfterDelete));
    }
    

    @Test
    public void testDeleteNotificationsBySenderId(){
        List<Notification> notificationsBeforeDelete = notificationRepository.findNotificationsBySenderId(1);
        notificationRepository.deleteNotificationsBySenderId(1);
        List<Notification> notificationsAfterDelete = notificationRepository.findNotificationsBySenderId(1);
        assertTrue(!notificationsBeforeDelete.equals(notificationsAfterDelete));
    }
}
