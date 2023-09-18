package com.taskagile.web.results;

import java.util.HashMap;

import org.springframework.util.Assert;

/**
 * API 요청에 대한 응답의 구조를 정의하고 데이터를 저장합니다.
 */
public class ApiResult extends HashMap<String, Object> {
  private static final long serialVersionUID = 4235569204L;

  private static final String MESSAGE_KEY = "message";
  private static final String ERROR_CODE_KEY = "errorReferenceCode";

  public static ApiResult blank() {
    return new ApiResult();
  }

  public static ApiResult message(String message) {
    Assert.hasText(message, "`message` 파라미터는 빈 값이 될 수 없습니다.");

    ApiResult apiResult = new ApiResult();
    apiResult.put("message", message);
    return apiResult;
  }

  public static ApiResult error(String message, String errorReferenceCode) {
    Assert.hasText(message, "`message` 파라미터는 빈 값이 될 수 없습니다.");

    ApiResult apiResult = new ApiResult();
    apiResult.put(MESSAGE_KEY, message);
    apiResult.put(ERROR_CODE_KEY, errorReferenceCode);
    return apiResult;
  }

  public ApiResult add(String key, Object value) {
    Assert.hasText(key, "`key` 파라미터는 빈 값이 될 수 없습니다.");
    Assert.notNull(value, "`value` 파라미터는 빈 값이 될 수 없습니다.");

    this.put(key, value);
    return this;
  }
}
