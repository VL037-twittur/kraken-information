package vincentlow.twittur.information.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notification")
@Data
public class Notification extends BaseEntity {

  @Column(name = "sender_id")
  private String senderId; // if "SYSTEM" than it's a system-generated notification, else account id??

  @Column(name = "recipient_id")
  private String recipientId; // accountProfile ID

  @Column(name = "title")
  private String title;

  @Column(name = "message")
  private String message;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "redirect_url")
  private String redirectUrl;

  @Column(name = "type")
  private String type;

  @Column(name = "has_read")
  private boolean hasRead;
}
