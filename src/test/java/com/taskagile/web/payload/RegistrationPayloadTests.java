package com.taskagile.web.payload;

import java.util.Set;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 회원가입 폼 데이터 검증 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class RegistrationPayloadTests {
  private Validator validator;

  @BeforeEach
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void validate_blankPayload_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(4, violations.size());
  }

  @Test
  public void validate_payloadWithInvalidEmail_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setEmailAddress("BadEmailAddress");
    payload.setUsername("MyUsername");
    payload.setPassword("MyPassword@");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithEmailAddressLongerThan100_shouldFail() {
    // rfc에 따라서 이메일 주소 로컬 파트는 최대 64글자 까지만 허용합니다.
    // http://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690
    int maxLocalParthLength = 64;
    String localPart = RandomStringUtils.random(maxLocalParthLength, true, true);
    int usedLength = maxLocalParthLength + "@".length() + ".com".length();
    String domain = RandomStringUtils.random(101 - usedLength, true, true);

    RegistrationPayload payload = new RegistrationPayload();
    payload.setEmailAddress(localPart + "@" + domain + ".com");
    payload.setUsername("MyUsername");
    payload.setPassword("MyPassword@");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithUsernameShorterThan2_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    String usernameTooShort = RandomStringUtils.random(1);
    payload.setUsername(usernameTooShort);
    payload.setPassword("MyPassword@");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithUsernameLongerThan50_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    String usernameTooLong = RandomStringUtils.random(51);
    payload.setUsername(usernameTooLong);
    payload.setPassword("MyPassword@");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithPasswordShorterThan6_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    String passwordTooShort = RandomStringUtils.random(4);
    payload.setPassword(passwordTooShort + "@");
    payload.setUsername("MyUsername");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithPasswordLongerThan30_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    String passwordTooLong = RandomStringUtils.random(31);
    payload.setPassword(passwordTooLong + "@");
    payload.setUsername("MyUsername");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithoutSpecialCharacterPassword_shouldFail() {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setPassword("MyPassword");
    payload.setUsername("MyUsername");
    payload.setEmailAddress("sunny@agilevolve.com");

    Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }
}
