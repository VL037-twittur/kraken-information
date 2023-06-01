package vincentlow.twittur.information.util;

import java.util.Objects;

import vincentlow.twittur.information.client.model.response.AccountCredentialResponse;
import vincentlow.twittur.information.client.model.response.AccountProfileResponse;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.model.entity.EmailTemplate;
import vincentlow.twittur.information.web.model.response.exception.BadRequestException;
import vincentlow.twittur.information.web.model.response.exception.NotFoundException;

public class ValidatorUtil {

  public static void validateState(boolean expression, String errorMessage) {

    if (!expression) {
      throw new BadRequestException(errorMessage);
    }
  }

  public static void validateArgument(boolean expression, String errorMessage) {

    if (!expression) {
      throw new BadRequestException(errorMessage);
    }
  }

  public static void validatePageableRequest(int pageNumber, int pageSize) {

    if (pageNumber < 0) {
      throw new BadRequestException(ExceptionMessage.PAGE_NUMBER_MUST_BE_AT_LEAST_0);
    } else if (pageSize < 1 || pageSize > 100) {
      throw new BadRequestException(ExceptionMessage.PAGE_SIZE_MUST_BE_BETWEEN_1_AND_100);
    }
  }

  public static AccountProfileResponse validateAccount(AccountProfileResponse account, String errorMessage) {

    if (Objects.isNull(account)) {
      throw new NotFoundException(errorMessage);
    }
    return account;
  }

  public static void validateAccount(AccountCredentialResponse account, String errorMessage) {

    if (Objects.isNull(account)) {
      throw new NotFoundException(errorMessage);
    }
  }

  public static void validateEmailTemplate(EmailTemplate template) {

    if (Objects.isNull(template)) {
      throw new NotFoundException(ExceptionMessage.EMAIL_TEMPLATE_NOT_FOUND);
    }
  }
}
