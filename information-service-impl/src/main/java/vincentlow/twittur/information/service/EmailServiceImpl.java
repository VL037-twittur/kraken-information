package vincentlow.twittur.information.service;

import static vincentlow.twittur.information.util.ValidatorUtil.*;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.client.AccountCredentialFeignClient;
import vincentlow.twittur.information.client.model.response.AccountCredentialResponse;
import vincentlow.twittur.information.model.constant.EmailTemplateCode;
import vincentlow.twittur.information.model.constant.ErrorCode;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.model.entity.Email;
import vincentlow.twittur.information.model.entity.EmailTemplate;
import vincentlow.twittur.information.repository.EmailRepository;
import vincentlow.twittur.information.repository.EmailTemplateRepository;
import vincentlow.twittur.information.web.model.request.EmailRequest;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private AccountCredentialFeignClient accountCredentialFeignClient;

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private EmailTemplateRepository emailTemplateRepository;

  @Override
  public Email sendEmail(EmailRequest request) {

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getRecipient()),
        ErrorCode.EMAIL_RECIPIENT_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getTemplateCode()),
        ErrorCode.EMAIL_TEMPLATE_CODE_MUST_NOT_BE_BLANK.getMessage());

    EmailTemplate emailTemplate =
        emailTemplateRepository.findByTemplateCodeAndMarkForDeleteFalse(request.getTemplateCode());
    validateEmailTemplate(emailTemplate);

    if (request.getTemplateCode()
        .equals(EmailTemplateCode.AFTER_REGISTRATION)) {
      validateArgument(StringUtils.isNotBlank(request.getUsername()),
          ErrorCode.USERNAME_MUST_NOT_BE_BLANK.getMessage());

      emailTemplate.setBody(emailTemplate.getBody()
          .replace("{{username}}", request.getUsername()));
    } else {
      ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> accountResponse =
          accountCredentialFeignClient.getAccountCredentialByEmailAddress(request.getRecipient());
      AccountCredentialResponse account = accountResponse.getBody()
          .getData();
      validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);
    }

    Email email = new Email();
    email.setRecipient(request.getRecipient());
    email.setSubject(emailTemplate.getSubject());
    email.setBody(emailTemplate.getBody());

    LocalDateTime now = LocalDateTime.now();
    email.setCreatedBy("KRAKEN-INFORMATION");
    email.setCreatedDate(now);
    email.setUpdatedBy("KRAKEN-INFORMATION");
    email.setUpdatedDate(now);

    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(email.getRecipient());
      helper.setSubject(email.getSubject());
      helper.setText(email.getBody(), true);

      email.setSent(true);
      javaMailSender.send(message);
    } catch (MessagingException e) {
      log.error("#EmailServiceImpl#send ERROR! with email: {}, and error: {}", email, e.getMessage(), e);
      email.setSent(false);
    }
    return emailRepository.save(email);
  }
}
