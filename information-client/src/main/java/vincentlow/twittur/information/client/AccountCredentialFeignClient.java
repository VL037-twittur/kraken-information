package vincentlow.twittur.information.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.information.client.model.response.AccountCredentialResponse;

@FeignClient(name = "cyclops-account-credential")
public interface AccountCredentialFeignClient {

  @GetMapping("api/v1/acc-cred/email/{emailAddress}")
  ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> getAccountCredentialByEmailAddress(
      @PathVariable String emailAddress);
}
