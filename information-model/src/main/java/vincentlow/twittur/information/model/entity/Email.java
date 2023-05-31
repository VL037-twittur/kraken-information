package vincentlow.twittur.information.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "email")
@Data
public class Email extends BaseEntity {

  @Column(name = "recipient")
  private String recipient;

  @Column(name = "subject")
  private String subject;

  @Column(name = "body", length = Integer.MAX_VALUE)
  private String body;

  @Column(name = "is_sent")
  private Boolean sent;
}
