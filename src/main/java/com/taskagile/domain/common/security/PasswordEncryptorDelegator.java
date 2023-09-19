package com.taskagile.domain.common.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {
  @Override
  public String encrypt(String rawPassword) {
    // 이곳에 구현
    return rawPassword;
  }
}
