package com.agilevolve.domain.model.user;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 유저(찾기) 관련 도메인 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
public class UserFinderTests {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserFinder userFinder;

  @BeforeEach
  void setUp() throws IllegalAccessException {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void find_existEmailAddress_shouldSucceed() throws IllegalAccessException, UserNotFoundException {
    // Arrange
    String emailAddress = "test@agilevolve.com";
    User expectedEmailAddress = new User();
    FieldUtils.writeField(expectedEmailAddress, "emailAddress", emailAddress, true);

    when(userRepository.findByEmailAddress(emailAddress)).thenReturn(expectedEmailAddress);

    // Act
    User actualUser = userFinder.find(emailAddress);

    // Assert
    assertEquals(expectedEmailAddress, actualUser);
    verify(userRepository, times(1)).findByEmailAddress(emailAddress);
  }

  @Test
  public void find_existUsername_shouldSucceed() throws IllegalAccessException, UserNotFoundException {
    // Arrange
    String username = "sunny";
    User expectedUser = new User();
    FieldUtils.writeField(expectedUser, "username", username, true);

    when(userRepository.findByUsername(username)).thenReturn(expectedUser);

    // Act
    User actualUser = userFinder.find(username);

    // Assert
    assertEquals(expectedUser, actualUser);
    verify(userRepository, times(1)).findByUsername(username);
  }

  @Test
  public void find_nonExistUsernameOrEmailAddress_shouldFailed() {
    // Arrange
    String usernameOrEmailAddress = "nonExistUser";

    when(userRepository.findByUsername(usernameOrEmailAddress)).thenReturn(null);
    when(userRepository.findByEmailAddress(usernameOrEmailAddress)).thenReturn(null);

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> userFinder.find(usernameOrEmailAddress));
  }
}
