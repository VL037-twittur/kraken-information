package vincentlow.twittur.information.web.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // needed when req field = 1
@AllArgsConstructor
public class GetNotificationRequest implements Serializable {

  private static final long serialVersionUID = 8637193882292350214L;

  private String recipientId;
}
