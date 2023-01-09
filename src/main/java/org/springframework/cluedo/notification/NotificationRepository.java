package org.springframework.cluedo.notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.repository.query.Param;


@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer>{
    
    List<Notification> findAll();

    @Query("SELECT u FROM Notification u where u.receiver.id=:id")
    List<Notification> findNotificationsByReceiverId(@Param("id")Integer id);


}
