package com.agilevolve.domain.application.impl;

import jakarta.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.agilevolve.domain.application.UserService;
import com.agilevolve.domain.application.commands.RegistrationCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.common.mail.MailManager;
import com.agilevolve.domain.common.mail.MessageVariable;
import com.agilevolve.domain.model.user.RegistrationException;
import com.agilevolve.domain.model.user.RegistrationManagement;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserRepository;
import com.agilevolve.domain.model.user.events.UserRegisteredEvent;

import org.apache.commons.lang3.StringUtils;

/**
 * 사용자 관련 비즈니스 로직
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
  private RegistrationManagement registrationManagement;
  private DomainEventPublisher domainEventPublisher;
  private MailManager mailManager;
  private UserRepository userRepository;

  public UserServiceImpl(RegistrationManagement registrationManagement,
      DomainEventPublisher domainEventPublisher,
      MailManager mailManager, UserRepository userRepository) {
    this.registrationManagement = registrationManagement;
    this.domainEventPublisher = domainEventPublisher;
    this.mailManager = mailManager;
    this.userRepository = userRepository;
  }

  @Override
  public void register(RegistrationCommand command) throws RegistrationException {
    Assert.notNull(command, "`command` 파라미터는 null이면 안됩니다.");
    // 애플리케이션 코어 내부와 외부 분리를 위해 RegistrationCommand를 사용하지 않습니다.
    User newUser = registrationManagement.register(
        command.getUsername(),
        command.getEmailAddress(),
        command.getPassword());

    sendWelcomeMessage(newUser);
    domainEventPublisher.publish(new UserRegisteredEvent(this, newUser));
  }

  private void sendWelcomeMessage(User user) {
    mailManager.send(
        user.getEmailAddress(),
        "Welcome to AgilEvolve",
        "welcome.ftl",
        MessageVariable.from("user", user));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (StringUtils.isEmpty(username)) {
      throw new UsernameNotFoundException("사용자명이 비어있습니다.");
    }
    User user;
    if (username.contains("@")) {
      user = userRepository.findByEmailAddress(username);
    } else {
      user = userRepository.findByUsername(username);
    }
    if (user == null) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. 사용자명: `" + username + "`");
    }
    return new SimpleUser(user);
  }
}
