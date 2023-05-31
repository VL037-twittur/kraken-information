package vincentlow.twittur.information.web.controller;

import static vincentlow.twittur.information.util.ObjectMappingHelper.toResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.model.constant.ApiPath;
import vincentlow.twittur.information.model.entity.Email;
import vincentlow.twittur.information.service.EmailService;
import vincentlow.twittur.information.web.model.request.EmailRequest;
import vincentlow.twittur.information.web.model.response.EmailResponse;

@Slf4j
@RestController
@RequestMapping(value = ApiPath.EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController extends BaseController {

  @Autowired
  private EmailService emailService;

  @PostMapping
  private ResponseEntity<ApiSingleResponse<EmailResponse>> sendEmail(@RequestBody EmailRequest request) {

    try {
      Email email = emailService.sendEmail(request);
      EmailResponse response = toResponse(email, EmailResponse.class);

      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (RuntimeException e) {
      log.error("#EmailController#sendEmail ERROR! with request: {}, and error: {}", request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
