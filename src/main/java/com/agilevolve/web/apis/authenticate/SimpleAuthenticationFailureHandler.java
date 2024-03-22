package com.agilevolve.web.apis.authenticate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.agilevolve.utils.JsonUtils;
import com.agilevolve.web.results.ApiResult;

public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    ApiResult failure;
    if (exception instanceof BadCredentialsException) {
      failure = ApiResult.message("유효하지 않은 자격입니다.");
    } else if (exception instanceof InsufficientAuthenticationException) {
      failure = ApiResult.message("유효하지 않은 인증 요청입니다.");
    } else {
      failure = ApiResult.message("인증에 실패하였습니다.");
    }
    JsonUtils.write(response.getWriter(), failure);
  }
}
