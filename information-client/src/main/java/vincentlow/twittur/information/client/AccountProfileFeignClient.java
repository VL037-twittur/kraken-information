package vincentlow.twittur.information.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vincentlow.twittur.base.web.model.response.api.ApiListResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.client.model.response.AccountFollowerResponse;
import vincentlow.twittur.information.client.model.response.AccountProfileResponse;

@FeignClient(name = "gargoyle-account-profile")
public interface AccountProfileFeignClient {

  @GetMapping("api/v1/accounts/{id}")
  ResponseEntity<ApiSingleResponse<AccountProfileResponse>> getAccountById(@PathVariable String id);

  @GetMapping("api/v1/accounts/{profileId}/all-followers")
  ResponseEntity<ApiListResponse<AccountFollowerResponse>> getAllAccountFollowers(@PathVariable String profileId);
}
