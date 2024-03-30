package com.agilevolve.domain.application;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.agilevolve.domain.application.commands.RegistrationCommand;
import com.agilevolve.domain.model.user.RegistrationException;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;

public interface UserService extends UserDetailsService {

  /**
   * 유저id로 사용자 찾기
   * 
   * @param userId 유저 id
   * @return 유저 객체 또는 찾지 못할 경우 null 반환
   */
  User findById(UserId userId);

  /**
   * 사용자명, 이메일, 패스워드로 새로운 사용자를 등록한다.
   *
   * @param command <code>RegistrationCommand</code>의 인스턴스
   * @throws RegistrationException 회원가입에 실패했을 때 다음과 같은 이유가 있을 수 있다.
   *                               1) 사용자명이 이미 존재함.
   *                               2) 이메일이 이미 존재함.
   */
  void register(RegistrationCommand command) throws RegistrationException;

}
