package org.springframework.cluedo.notifications;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cluedo.notification.Notification;
import org.springframework.cluedo.notification.NotificationRepository;

@DataJpaTest
public class NotificationRepositoryTest {
    
    @Autowired 
    NotificationRepository notificationRepository;
    
    @Test
    public void testFindNotificationsByReceiverId(){
        List<Notification> notifications= notificationRepository.findNotificationsByReceiverId(2);
        assertFalse(notifications.isEmpty());
    }

    @Test
    public void testFindNotificationsBySenderId(){
        List<Notification> notifications= notificationRepository.findNotificationsBySenderId(1);
        assertFalse(notifications.isEmpty());
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
