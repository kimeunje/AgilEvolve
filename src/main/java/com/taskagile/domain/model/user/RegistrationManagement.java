package com.taskagile.domain.model.user;

import org.springframework.stereotype.Component;

import com.taskagile.domain.common.security.PasswordEncryptorDelegator;

/**
 * 사용자(회원가입) 관련 도메인 서비스
 */
@Component
public class RegistrationManagement {
  private UserRepository repository;
  private PasswordEncryptorDelegator passwordEncryptor;

  public RegistrationManagement(UserRepository repository, PasswordEncryptorDelegator passwordEncryptor) {
    this.repository = repository;
    this.passwordEncryptor = passwordEncryptor;
  }

  public User register(String username, String emailAddress, String password) throws RegistrationException {
    User existingUser = repository.findByUsername(username);
    if (existingUser != null) {
      throw new UsernameExistsException();
    }

    existingUser = repository.findByEmailAddress(emailAddress.toLowerCase());
    if (existingUser != null) {
      throw new EmailAddressExistsException();
    }

    String encryptedPassword = passwordEncryptor.encrypt(password);
    User newUser = User.create(username, emailAddress.toLowerCase(), encryptedPassword);
    repository.save(newUser);
    return newUser;
  }
}
