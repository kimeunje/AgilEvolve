package com.agilevolve.web.apis;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agilevolve.domain.application.UserService;
import com.agilevolve.domain.model.user.EmailAddressExistsException;
import com.agilevolve.domain.model.user.RegistrationException;
import com.agilevolve.domain.model.user.UsernameExistsException;
import com.agilevolve.web.payload.RegistrationPayload;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.Result;

/**
 * 회원가입 API 요청을 처리하는 컨트롤러
 */
@Controller
public class RegistrationApiController {
  private UserService service;

  public RegistrationApiController(UserService service) {
    this.service = service;
  }

  @PostMapping("/api/registrations")
  public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistrationPayload payload) {
    try {
      service.register(payload.toCommand());
      return Result.created();
    } catch (RegistrationException e) {
      String errorMessage = "회원가입에 실패하였습니다.";
      if (e instanceof UsernameExistsException) {
        errorMessage = "이미 사용중인 사용자명입니다.";
      } else if (e instanceof EmailAddressExistsException) {
        errorMessage = "이미 사용중인 이메일입니다.";
      }
      return Result.failure(errorMessage);
    }
  }
}
