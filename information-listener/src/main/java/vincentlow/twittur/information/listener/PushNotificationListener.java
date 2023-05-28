package vincentlow.twittur.information.listener;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.model.response.api.ApiListResponse;
import vincentlow.twittur.information.client.AccountProfileFeignClient;
import vincentlow.twittur.information.client.model.response.AccountFollowerResponse;
import vincentlow.twittur.information.model.constant.KafkaConstant;
import vincentlow.twittur.information.model.entity.Notification;
import vincentlow.twittur.information.repository.NotificationRepository;

@Slf4j
@Component
public class PushNotificationListener {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private AccountProfileFeignClient accountProfileFeignClient;

  @KafkaListener(topics = KafkaConstant.PUSH_TWEET_NOTIFICATION, groupId = KafkaConstant.GROUP_ID)
  public void processNewTweetNotification(ConsumerRecord<String, String> record) {

    try {
      Notification baseNotification = objectMapper.readValue(record.value(), Notification.class);

      LocalDateTime now = LocalDateTime.now();
      baseNotification.setCreatedBy(baseNotification.getSenderId());
      baseNotification.setCreatedDate(now);
      baseNotification.setUpdatedBy(baseNotification.getSenderId());
      baseNotification.setUpdatedDate(now);

      ResponseEntity<ApiListResponse<AccountFollowerResponse>> followersResponse =
          accountProfileFeignClient.getAllAccountFollowers(baseNotification.getSenderId());
      List<AccountFollowerResponse> followers = followersResponse.getBody()
          .getContent();

      for (AccountFollowerResponse follower : followers) {
        Notification followerNotification = new Notification();
        BeanUtils.copyProperties(baseNotification, followerNotification);

        followerNotification.setRecipientId(follower.getId());
        notificationRepository.save(followerNotification);
      }
    } catch (Exception e) {
      log.error("#PushNotificationListener#processNewTweetNotification ERROR! with record: {}, and error: {}", record,
          e.getMessage(), e);
    }
  }
}
