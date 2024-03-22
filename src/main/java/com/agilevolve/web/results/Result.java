package com.agilevolve.web.results;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

/**
 * API 요청에 대한 응답을 생성하고 반환하는 메서드를 제공합니다.
 */
public class Result {
  private Result() {
  }

  public static ResponseEntity<ApiResult> created() {
    return ResponseEntity.status(201).build();
  }

  public static ResponseEntity<ApiResult> ok() {
    return ResponseEntity.ok().build();
  }

  public static ResponseEntity<ApiResult> ok(String message) {
    Assert.hasText(message, "`message` 파라미터는 빈 값이 될 수 없습니다.");

    return ok(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> ok(ApiResult payload) {
    Assert.notNull(payload, "`payload` 파라미터는 빈 값이 될 수 없습니다.");

    return ResponseEntity.ok(payload);
  }

  public static ResponseEntity<ApiResult> failure(String message) {
    return ResponseEntity.badRequest().body(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> serverError(String message, String errorReferenceCode) {
    return ResponseEntity.status(500).body(ApiResult.error(message, errorReferenceCode));
  }

  public static ResponseEntity<ApiResult> notFound() {
    return ResponseEntity.notFound().build();
  }

  public static ResponseEntity<ApiResult> unauthenticated() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  public static ResponseEntity<ApiResult> forbidden() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
