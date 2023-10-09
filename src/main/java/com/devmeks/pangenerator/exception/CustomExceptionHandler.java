package com.devmeks.pangenerator.exception;

import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.model.ApiError;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * The type Custom exception handler.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private final ResponseDto errorResponse = new ResponseDto();
  /**
   * The Api error.
   */
  ApiError apiError = ApiError.ceateApiError();

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
    String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return handleAllExceptions(ex, errorMessage, status);
  }


  /**
   * Handle all exceptions response entity.
   *
   * @param ex           the ex
   * @param errorMessage the error message
   * @param status       the status
   * @return the response entity
   */
  public ResponseEntity<Object> handleAllExceptions(Exception ex,
                                                    String errorMessage,
                                                    HttpStatus status) {


    switch (ex.getClass().getSimpleName()) {
      case "NullPointerException":
        log.error("A null pointer exception has occurred");
        break;
      case "ValidationException":
        log.error("A validation error has occurred");
        break;
      case "MethodArgumentNotValidException":
        log.error("A validation error has occurred: {}...", errorMessage);
        break;
      default:
        log.error("An error of type {} has occurred", ex.getClass().getSimpleName());


    }
    apiError.setErrorMessage(errorMessage);
    errorResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    errorResponse.setError(apiError);

    return ResponseEntity.status(status).body(errorResponse);
  }


}
