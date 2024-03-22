package com.agilevolve.domain.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.agilevolve.domain.common.security.PasswordEncryptorDelegator;
import com.agilevolve.domain.model.user.EmailAddressExistsException;
import com.agilevolve.domain.model.user.RegistrationException;
import com.agilevolve.domain.model.user.RegistrationManagement;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserRepository;
import com.agilevolve.domain.model.user.UsernameExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 사용자(회원가입) 관련 도메인 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
public class RegistrationManagementTests {

  private UserRepository repositoryMock;
  private PasswordEncryptorDelegator passwordEncryptorMock;
  private RegistrationManagement instance;

  @BeforeEach
  public void setUp() {
    repositoryMock = mock(UserRepository.class);
    passwordEncryptorMock = mock(PasswordEncryptorDelegator.class);
    instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
  }

  @Test
  public void register_existedUsername_shouldFail() throws RegistrationException {
    String username = "existUsername";
    String emailAddress = "sunny@agilevolve.com";
    String password = "MyPassword!";

    when(repositoryMock.findByUsername(username)).thenReturn(new User());
    assertThrows(UsernameExistsException.class, () -> {
      instance.register(username, emailAddress, password);
    });
  }

  @Test
  public void register_existedEmailAddress_shouldFail() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "exist@agilevolve.com";
    String password = "MyPassword!";

    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
    assertThrows(EmailAddressExistsException.class, () -> {
      instance.register(username, emailAddress, password);
    });
  }

  @Test
  public void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "Sunny@agilevolve.com";
    String password = "MyPassword!";
    instance.register(username, emailAddress, password);
    User userToSave = User.create(username, emailAddress.toLowerCase(), password);
    verify(repositoryMock).save(userToSave);
  }

  @Test
  public void register_newUser_shouldSucceed() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "sunny@agilevolve.com";
    String password = "MyPassword!";
    String encryptedPassword = "EncryptedPassword";
    User newUser = User.create(username, emailAddress, encryptedPassword);

    when(repositoryMock.findByUsername(username)).thenReturn(null);
    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
    doNothing().when(repositoryMock).save(newUser);
    when(passwordEncryptorMock.encrypt(password)).thenReturn("EncryptedPassword");

    User savedUser = instance.register(username, emailAddress, password);
    InOrder inOrder = inOrder(repositoryMock);
    inOrder.verify(repositoryMock).findByUsername(username);
    inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
    inOrder.verify(repositoryMock).save(newUser);
    verify(passwordEncryptorMock).encrypt(password);
    assertEquals(encryptedPassword, savedUser.getPassword(), "사용자의 암호가 암호화되어 저장되었습니다.");
  }
}
