package com.glanner.core.repository;

import com.glanner.core.domain.user.Notification;
import com.glanner.core.domain.user.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByConfirmation(NotificationStatus notificationStatus);
}
