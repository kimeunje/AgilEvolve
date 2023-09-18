package com.taskagile.domain.application.impl;

import org.springframework.stereotype.Service;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.model.user.RegistrationException;

/**
 * 사용자 관련 비즈니스 로직
 */
@Service
public class UserServiceImpl implements UserService {
  @Override
  public void register(RegistrationCommand command) throws RegistrationException {
    // 이곳에 구현 예정
  }
}
