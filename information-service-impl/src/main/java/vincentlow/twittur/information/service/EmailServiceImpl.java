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
import vincentlow.twittur.information.model.constant.ErrorCode;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.model.entity.Email;
import vincentlow.twittur.information.repository.EmailRepository;
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

  @Override
  public Email sendEmail(EmailRequest request) {

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getRecipient()),
        ErrorCode.EMAIL_RECIPIENT_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getSubject()),
        ErrorCode.EMAIL_SUBJECT_MUST_NOT_BE_BLANK.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getBody()),
        ErrorCode.EMAIL_BODY_MUST_NOT_BE_BLANK.getMessage());

    ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> accountResponse =
        accountCredentialFeignClient.getAccountCredentialByEmailAddress(request.getRecipient());
    AccountCredentialResponse account = accountResponse.getBody()
        .getData();

    validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);

    Email email = new Email();
    email.setRecipient(request.getRecipient());
    email.setSubject(request.getSubject());
    email.setBody(request.getBody());

    LocalDateTime now = LocalDateTime.now();
    email.setCreatedBy("system");
    email.setCreatedDate(now);
    email.setUpdatedBy("system");
    email.setUpdatedDate(now);

    String htmlBody = "<!DOCTYPE html>\n" +
        "<html>\n" +
        "<head>\n" +
        "  <title>Welcome to our Community!</title>\n" +
        "  <style>\n" +
        "    /* CSS styles for the email */\n" +
        "    body {\n" +
        "      background-color: #f2f2f2;\n" +
        "      font-family: Arial, sans-serif;\n" +
        "      color: #333333;\n" +
        "    }\n" +
        "    \n" +
        "    .container {\n" +
        "      max-width: 600px;\n" +
        "      margin: 0 auto;\n" +
        "      padding: 20px;\n" +
        "      background-color: #ffffff;\n" +
        "      border-radius: 10px;\n" +
        "      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
        "    }\n" +
        "    \n" +
        "    .header {\n" +
        "      background-color: #db4dff;\n" +
        "      padding: 20px;\n" +
        "      text-align: center;\n" +
        "      border-top-left-radius: 10px;\n" +
        "      border-top-right-radius: 10px;\n" +
        "    }\n" +
        "    \n" +
        "    .header h1 {\n" +
        "      color: #ffffff;\n" +
        "      font-size: 24px;\n" +
        "      margin: 0;\n" +
        "    }\n" +
        "    \n" +
        "    .content {\n" +
        "      padding: 20px;\n" +
        "    }\n" +
        "    \n" +
        "    .highlight {\n" +
        "      color: #db4dff;\n" +
        "      font-weight: bold;\n" +
        "    }\n" +
        "    \n" +
        "    .footer {\n" +
        "      background-color: #eeeeee;\n" +
        "      padding: 20px;\n" +
        "      text-align: center;\n" +
        "      border-bottom-left-radius: 10px;\n" +
        "      border-bottom-right-radius: 10px;\n" +
        "    }\n" +
        "    \n" +
        "    .footer p {\n" +
        "      margin: 0;\n" +
        "      color: #888888;\n" +
        "    }\n" +
        "  </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "  <div class=\"container\">\n" +
        "    <div class=\"header\">\n" +
        "      <h1>Welcome, @" + account.getUsername() + "!</h1>\n" +
        "    </div>\n" +
        "    <div class=\"content\">\n" +
        "      <p>Dear <span class=\"highlight\">@" + account.getUsername() + "</span>,</p>\n" +
        "      <p>Welcome to Twittur! We're excited to have you on board.</p>\n" +
        "      <p>Feel free to explore our website, join discussions, and connect with other members who share your interests.</p>\n"
        +
        "      <p>If you have any questions or need assistance, don't hesitate to reach out to our support team.</p>\n"
        +
        "      <p>Thank you again for joining. We look forward to seeing you around!</p>\n" +
        "    </div>\n" +
        "    <div class=\"footer\">\n" +
        "      <p>Best regards,</p>\n" +
        "      <p><b>Twittur Team</b></p>\n" +
        "    </div>\n" +
        "  </div>\n" +
        "</body>\n" +
        "</html>\n";

    email.setBody(htmlBody);
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
