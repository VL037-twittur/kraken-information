package vincentlow.twittur.information.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import vincentlow.twittur.base.web.model.response.BaseResponse;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResponse extends BaseResponse {

  private String title;

  private String message;

  private String imageUrl;

  private String redirectUrl;

  private String type;

  private boolean hasRead;
}
