package vincentlow.twittur.information.service;

import static vincentlow.twittur.information.util.ValidatorUtil.validateAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.client.AccountProfileFeignClient;
import vincentlow.twittur.information.client.model.response.AccountProfileResponse;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.model.entity.Notification;
import vincentlow.twittur.information.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private AccountProfileFeignClient accountProfileFeignClient;

  @Autowired
  private NotificationRepository notificationRepository;

  @Override
  public Page<Notification> getNotifications(String recipientId, int pageNumber, int pageSize) {

    ResponseEntity<ApiSingleResponse<AccountProfileResponse>> accountResponse =
        accountProfileFeignClient.getAccountById(recipientId);
    AccountProfileResponse account = accountResponse.getBody()
        .getData();

    validateAccount(account, ExceptionMessage.ACCOUNT_NOT_FOUND);

    return notificationRepository.findAllByRecipientIdOrderByCreatedDateDesc(recipientId,
        PageRequest.of(pageNumber, pageSize));
  }
}
