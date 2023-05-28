package vincentlow.twittur.information.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vincentlow.twittur.information.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

  Page<Notification> findAllByRecipientIdOrderByCreatedDateDesc(String recipientId, Pageable pageable);
}
