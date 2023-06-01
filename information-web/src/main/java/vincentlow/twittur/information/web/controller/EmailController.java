package vincentlow.twittur.information.web.controller;

import static vincentlow.twittur.information.util.ObjectMappingHelper.toResponse;
import static vincentlow.twittur.information.util.ValidatorUtil.validatePageableRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.PageMetaData;
import vincentlow.twittur.base.web.model.response.api.ApiListResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.model.constant.ApiPath;
import vincentlow.twittur.information.model.entity.Email;
import vincentlow.twittur.information.model.entity.EmailTemplate;
import vincentlow.twittur.information.service.EmailService;
import vincentlow.twittur.information.service.EmailTemplateService;
import vincentlow.twittur.information.web.model.request.CreateEmailTemplateRequest;
import vincentlow.twittur.information.web.model.request.EmailRequest;
import vincentlow.twittur.information.web.model.response.EmailResponse;
import vincentlow.twittur.information.web.model.response.EmailTemplateResponse;

@Slf4j
@RestController
@RequestMapping(value = ApiPath.EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController extends BaseController {

  @Autowired
  private EmailService emailService;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @PostMapping
  public ResponseEntity<ApiSingleResponse<EmailResponse>> sendEmail(@RequestBody EmailRequest request) {

    try {
      Email email = emailService.sendEmail(request);
      EmailResponse response = toResponse(email, EmailResponse.class);

      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#EmailController#sendEmail ERROR! with request: {}, and error: {}", request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @GetMapping("/templates")
  public ResponseEntity<ApiListResponse<EmailTemplateResponse>> getEmailTemplates(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize) {

    try {
      validatePageableRequest(pageNumber, pageSize);

      Page<EmailTemplate> emails = emailTemplateService.getEmailTemplates(pageNumber, pageSize);
      List<EmailTemplateResponse> response = emails.stream()
          .map(email -> toResponse(email, EmailTemplateResponse.class))
          .collect(Collectors.toList());
      PageMetaData pageMetaData = getPageMetaData(emails, pageNumber, pageSize);

      return toSuccessResponseEntity(toApiListResponse(response, pageMetaData));
    } catch (RuntimeException e) {
      log.error("#EmailController#getEmailTemplates ERROR! with pageNumber: {}, pageSize: {}, and error: {}",
          pageNumber, pageSize, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @PostMapping("/templates")
  public ResponseEntity<ApiSingleResponse<EmailTemplateResponse>> createEmailTemplate(
      @RequestBody CreateEmailTemplateRequest request) {

    try {
      EmailTemplate emailTemplate = emailTemplateService.createEmailTemplate(request);
      EmailTemplateResponse response = toResponse(emailTemplate, EmailTemplateResponse.class);

      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#EmailController#createEmailTemplate ERROR! with request: {}, and error: {}", request, e.getMessage(),
          e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
