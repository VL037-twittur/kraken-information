package vincentlow.twittur.information.web.controller;

import static vincentlow.twittur.information.util.ObjectMappingHelper.toResponse;
import static vincentlow.twittur.information.util.ValidatorUtil.validatePageableRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.PageMetaData;
import vincentlow.twittur.base.web.model.response.api.ApiListResponse;
import vincentlow.twittur.information.model.constant.ApiPath;
import vincentlow.twittur.information.model.entity.Notification;
import vincentlow.twittur.information.service.NotificationService;
import vincentlow.twittur.information.web.model.request.GetNotificationRequest;
import vincentlow.twittur.information.web.model.response.NotificationResponse;

@Slf4j
@RestController
@RequestMapping(value = ApiPath.NOTIFICATION, produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController extends BaseController {

  private final int DEFAULT_PAGE_SIZE = 10;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @GetMapping
  public ResponseEntity<ApiListResponse<NotificationResponse>> getNotifications(
      @RequestBody GetNotificationRequest request,
      @RequestParam(defaultValue = "0") int pageNumber) {

    try {
      validatePageableRequest(pageNumber, DEFAULT_PAGE_SIZE);

      kafkaTemplate.send("vincentlow.twittur.griffin.tweet.push.notification", "PERCOBAAN AJA");

      Page<Notification> notifications =
          notificationService.getNotifications(request.getRecipientId(), pageNumber, DEFAULT_PAGE_SIZE);
      List<NotificationResponse> response = notifications.stream()
          .map(notification -> toResponse(notification, NotificationResponse.class))
          .collect(Collectors.toList());
      PageMetaData pageMetaData = getPageMetaData(notifications, pageNumber, DEFAULT_PAGE_SIZE);

      return toSuccessResponseEntity(toApiListResponse(response, pageMetaData));
    } catch (Exception e) {
      log.error("#NotificationController#getNotifications ERROR! with request: {}, pageNumber: {}, and error: {}",
          request, pageNumber, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
