package com.taskagile.web.apis;

import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler({ Exception.class })
  protected ResponseEntity<ApiResult> handle(RuntimeException ex) {
    String errorReferenceCode = UUID.randomUUID().toString();
    log.error("처리되지 않은 예외 오류 [code=" + errorReferenceCode + "]", ex);
    return Result.serverError("죄송합니다. 서버 측에 오류가 발생했습니다.", errorReferenceCode);
  }
}
