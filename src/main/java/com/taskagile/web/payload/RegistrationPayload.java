package com.taskagile.web.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.taskagile.domain.application.commands.RegistrationCommand;
/**
 * 회원가입에 사용되는 데이터를 담고 있는 객체
 *
 * 페이로드는 회원가입 데이터 전송과 관련된 관심사를 처리하는 객체입니다.
 */
public class RegistrationPayload {
  @Size(min = 2, max = 50, message = "사용자명은 2글자와 50글자 사이여야 합니다.")
  @NotNull
  private String username;

  @Email(message = "이메일 주소는 유효해야 합니다.")
  @Size(max = 100, message = "이메일 주소는 100글자를 초과할 수 없습니다.")
  @NotNull
  private String emailAddress;

  @Size(min = 6, max = 30, message = "비밀번호는 6글자와 30글자 사이여야 합니다.")
  @NotNull
  private String password;

  public RegistrationCommand toCommand() {
    return new RegistrationCommand(this.username, this.emailAddress, this.password);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
