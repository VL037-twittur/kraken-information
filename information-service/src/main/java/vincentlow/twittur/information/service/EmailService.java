package vincentlow.twittur.information.service;

import vincentlow.twittur.information.model.entity.Email;
import vincentlow.twittur.information.web.model.request.EmailRequest;

public interface EmailService {

  Email sendEmail(EmailRequest request);
}
