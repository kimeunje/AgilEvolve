package com.taskagile.domain.model.user;

/**
 * 사용자 관련 인프라 서비스
 *
 * JDBC, JPA 등 사용을 위해서 확장성으로 분리
 */
public interface UserRepository {
  /**
   * 사용자명으로 사용자 찾기
   *
   * @param username 사용자를 찾기 위해 제공된 사용자명
   * @return 찾을 경우 <code>User</code>의 인스턴스 반환, 못 찾을 경우 null 반환
   */
  User findByUsername(String username);

  /**
   * 이메일로 사용자 찾기
   *
   * @param emailAddress 사용자를 찾기 위해 제공된 이메일
   * @return 찾을 경우 <code>User</code>의 인스턴스 반환, 못 찾을 경우 null 반환
   */
  User findByEmailAddress(String emailAddress);

  /**
   * 새로운 사용자 또는 존재하는 사용자 정보 저장
   *
   * @param user 저장된 유저 인스턴스
   * @return 새롭게 저장된 유저
   */
  void save(User user);
}
