package com.taskagile.domain.application.commands;

/**
 * 회원가입에 사용되는 데이터를 담고 있는 객체
 *
 * 커맨드는 회원가입 비즈니스 로직과 관련된 관심사를 처리하는 객체입니다.
 * 애플리케이션 서비스(컨트롤러 등)의 클라이언트가 활용하는 데이터 객체입니다.
 */
public class RegistrationCommand {
  private String username;
  private String emailAddress;
  private String password;

  public RegistrationCommand(String username, String emailAddress, String password) {
    this.username = username;
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    RegistrationCommand that = (RegistrationCommand) o;
    if (username != null ? !username.equals(that.username) : that.username != null)
      return false;
    if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null)
      return false;
    return password != null ? password.equals(that.password) : that.password == null;
  }

  @Override
  public int hashCode() {
    int result = username != null ? username.hashCode() : 0;
    result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RegistrationCommand{" +
        "username='" + username + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
