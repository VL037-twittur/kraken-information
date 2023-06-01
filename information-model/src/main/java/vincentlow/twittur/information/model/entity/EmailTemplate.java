package vincentlow.twittur.information.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "email_template")
@Data
public class EmailTemplate extends BaseEntity {

  @Column(name = "template_code")
  private String templateCode;

  @Column(name = "subject")
  private String subject;

  @Column(name = "body", length = Integer.MAX_VALUE)
  private String body;
}
