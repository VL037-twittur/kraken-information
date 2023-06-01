package vincentlow.twittur.information.service;

import static vincentlow.twittur.information.util.ValidatorUtil.validateArgument;
import static vincentlow.twittur.information.util.ValidatorUtil.validateState;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import vincentlow.twittur.information.model.constant.ErrorCode;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.model.entity.EmailTemplate;
import vincentlow.twittur.information.repository.EmailTemplateRepository;
import vincentlow.twittur.information.web.model.request.CreateEmailTemplateRequest;
import vincentlow.twittur.information.web.model.response.exception.ConflictException;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

  @Autowired
  private EmailTemplateRepository emailTemplateRepository;

  @Override
  public Page<EmailTemplate> getEmailTemplates(int pageNumber, int pageSize) {

    return emailTemplateRepository.findAll(PageRequest.of(pageNumber, pageSize));
  }

  @Override
  public EmailTemplate createEmailTemplate(CreateEmailTemplateRequest request) {

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getTemplateCode()),
        ErrorCode.EMAIL_TEMPLATE_CODE_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getSubject()),
        ErrorCode.EMAIL_SUBJECT_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getBody()),
        ErrorCode.EMAIL_BODY_MUST_NOT_BE_BLANK.getMessage());

    EmailTemplate emailTemplate =
        emailTemplateRepository.findByTemplateCodeAndMarkForDeleteFalse(request.getTemplateCode());
    if (Objects.nonNull(emailTemplate)) {
      throw new ConflictException(ExceptionMessage.EMAIL_TEMPLATE_CODE_IS_TAKEN);
    }

    emailTemplate = new EmailTemplate();
    emailTemplate.setTemplateCode(request.getTemplateCode());
    emailTemplate.setSubject(request.getSubject());
    emailTemplate.setBody(request.getBody());

    LocalDateTime now = LocalDateTime.now();
    emailTemplate.setCreatedBy("KRAKEN-INFORMATION");
    emailTemplate.setCreatedDate(now);
    emailTemplate.setUpdatedBy("KRAKEN-INFORMATION");
    emailTemplate.setUpdatedDate(now);

    return emailTemplateRepository.save(emailTemplate);
  }
}
