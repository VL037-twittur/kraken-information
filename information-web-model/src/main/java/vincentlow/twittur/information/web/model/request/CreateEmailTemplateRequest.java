package vincentlow.twittur.information.web.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmailTemplateRequest implements Serializable {

  private static final long serialVersionUID = 720576531751094483L;

  private String templateCode;

  private String subject;

  private String body;
}
