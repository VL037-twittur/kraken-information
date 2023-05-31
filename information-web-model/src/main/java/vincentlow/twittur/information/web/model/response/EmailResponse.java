package vincentlow.twittur.information.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vincentlow.twittur.base.web.model.response.BaseResponse;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailResponse extends BaseResponse {

  private String recipient;

  private boolean sent;
}
