package vincentlow.twittur.information.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfileResponse {

  private String id;

  private String username;

  private String accountName;
}
