package vincentlow.twittur.information.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vincentlow.twittur.base.web.model.response.BaseResponse;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailTemplateResponse extends BaseResponse {

  private String templateCode;

  private String body;
}
