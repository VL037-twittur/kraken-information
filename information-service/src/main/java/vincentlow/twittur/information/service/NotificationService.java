package vincentlow.twittur.information.service;

import org.springframework.data.domain.Page;

import vincentlow.twittur.information.model.entity.Notification;

public interface NotificationService {

  Page<Notification> getNotifications(String recipientId, int pageNumber, int pageSize);
}
