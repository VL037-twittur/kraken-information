package vincentlow.twittur.information.web.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.api.ApiResponse;
import vincentlow.twittur.information.model.constant.ExceptionMessage;
import vincentlow.twittur.information.web.model.response.exception.BadRequestException;
import vincentlow.twittur.information.web.model.response.exception.NotFoundException;
import vincentlow.twittur.information.web.model.response.exception.ServiceUnavailableException;

@Slf4j
@RestControllerAdvice
public class ExceptionController extends BaseController {

  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex) {

    log.error("#handleBadRequestException ERROR! with error: {}", ex.getMessage());
    return toErrorResponseEntity(toErrorApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity handleNotFoundException(NotFoundException ex) {

    log.error("#handleNotFoundException ERROR! with error: {}", ex.getMessage());
    return toErrorResponseEntity(toErrorApiResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {DataAccessException.class, ServiceUnavailableException.class, MessagingException.class})
  public ResponseEntity handleServiceUnavailableException(RuntimeException ex) {

    log.error("#handleServiceUnavailableException ERROR! with error: {}", ex.getMessage());
    return toErrorResponseEntity(
        toErrorApiResponse(HttpStatus.SERVICE_UNAVAILABLE, ExceptionMessage.SERVICE_TEMPORARILY_UNAVAILABLE),
        HttpStatus.SERVICE_UNAVAILABLE);
  }
}
