package com.agilevolve.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserRepository;
import com.agilevolve.infrastructure.repository.HibernateUserRepository;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 사용자 관련 인프라 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTests {

  @TestConfiguration
  public static class UserRepositoryTestContextConfiguration {
    @Bean
    public UserRepository userRepository(EntityManager entityManager) {
      return new HibernateUserRepository(entityManager);
    }
  }

  @Autowired
  private UserRepository repository;

  @Test
  public void save_nullUsernameUser_shouldFail() {
    User inavlidUser = User.create(null, "sunny@agilevolve.com", "MyPassword!");

    assertThrows(PersistenceException.class, () -> {
      repository.save(inavlidUser);
    });
  }

  @Test
  public void save_nullEmailAddressUser_shouldFail() {
    User inavlidUser = User.create("sunny", null, "MyPassword!");

    assertThrows(PersistenceException.class, () -> {
      repository.save(inavlidUser);
    });
  }

  @Test
  public void save_nullPasswordUser_shouldFail() {
    User inavlidUser = User.create("sunny", "sunny@agilevolve.com", null);

    assertThrows(PersistenceException.class, () -> {
      repository.save(inavlidUser);
    });
  }

  @Test
  public void save_validUser_shouldSuccess() {
    String username = "sunny";
    String emailAddress = "sunny@agilevolve.com";
    User newUser = User.create(username, emailAddress, "MyPassword!");
    repository.save(newUser);
    assertNotNull(newUser.getId(), "새로운 사용자의 아이디가 생성되어야 합니다.");
    assertNotNull(newUser.getCreatedDate(), "새로운 사용자의 생성일이 생성되어야 합니다.");
    assertEquals(username, newUser.getUsername());
    assertEquals(emailAddress, newUser.getEmailAddress());
    assertEquals("", newUser.getFirstName());
    assertEquals("", newUser.getLastName());
  }

  @Test
  public void save_usernameAlreadyExist_shouldFail() {
    // Create already exist user
    String username = "sunny";
    String emailAddress = "sunny@agilevolve.com";
    User alreadyExist = User.create(username, emailAddress, "MyPassword!");
    repository.save(alreadyExist);

    try {
      User newUser = User.create("username", "new@agilevolve.com", "MyPassword!");
      repository.save(newUser);
    } catch (Exception e) {
      assertEquals(JdbcSQLIntegrityConstraintViolationException.class.toString(), e.getCause().getClass().toString());
    }
  }

  @Test
  public void save_emailAddressAlreadyExist_shouldFail() {
    // Create already exist user
    String username = "sunny";
    String emailAddress = "sunny@agilevolve.com";
    User alreadyExist = User.create(username, emailAddress, "MyPassword!");
    repository.save(alreadyExist);

    try {
      User newUser = User.create("new", emailAddress, "MyPassword!");
      repository.save(newUser);
    } catch (Exception e) {
      assertEquals(JdbcSQLIntegrityConstraintViolationException.class.toString(), e.getCause().getClass().toString());
    }
  }

  @Test
  public void findByEmailAddress_notExist_shouldReturnEmptyResult() {
    String emailAddress = "sunny@agilevolve.com";
    User user = repository.findByEmailAddress(emailAddress);
    assertNull(user, "No user should by found");
  }

  @Test
  public void findByEmailAddress_exist_shouldReturnResult() {
    String emailAddress = "sunny@agilevolve.com";
    String username = "sunny";
    User newUser = User.create(username, emailAddress, "MyPassword!");
    repository.save(newUser);
    User found = repository.findByEmailAddress(emailAddress);
    assertEquals(username, found.getUsername(), "Username should match");
  }

  @Test
  public void findByUsername_notExist_shouldReturnEmptyResult() {
    String username = "sunny";
    User user = repository.findByUsername(username);
    assertNull(user, "No user should by found");
  }

  @Test
  public void findByUsername_exist_shouldReturnResult() {
    String username = "sunny";
    String emailAddress = "sunny@agilevolve.com";
    User newUser = User.create(username, emailAddress, "MyPassword!");
    repository.save(newUser);
    User found = repository.findByUsername(username);
    assertEquals(emailAddress, found.getEmailAddress(), "Email address should match");
  }
}
