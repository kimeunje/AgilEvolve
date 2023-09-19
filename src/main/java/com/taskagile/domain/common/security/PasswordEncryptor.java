package com.taskagile.domain.common.security;

public interface PasswordEncryptor {
  /**
   * 비밀번호 암호화
   */
  String encrypt(String rawPassword);
}
