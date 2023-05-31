package vincentlow.twittur.information.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialResponse {

  String id;

  String username;

  String emailAddress;
}
