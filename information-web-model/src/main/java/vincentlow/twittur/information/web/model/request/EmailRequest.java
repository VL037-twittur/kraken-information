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
public class EmailRequest implements Serializable {

  private static final long serialVersionUID = -142800602376790028L;

  private String recipient;

  private String subject;

  private String body;
}
