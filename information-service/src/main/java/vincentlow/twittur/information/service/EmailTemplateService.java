package vincentlow.twittur.information.service;

import org.springframework.data.domain.Page;

import vincentlow.twittur.information.model.entity.EmailTemplate;
import vincentlow.twittur.information.web.model.request.CreateEmailTemplateRequest;

public interface EmailTemplateService {

  Page<EmailTemplate> getEmailTemplates(int pageNumber, int pageSize);

  EmailTemplate createEmailTemplate(CreateEmailTemplateRequest request);
}
