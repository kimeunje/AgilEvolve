package com.agilevolve.infrastructure.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.agilevolve.domain.common.mail.Mailer;
import com.agilevolve.domain.common.mail.Message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AsyncMailer implements Mailer {

  private static final Logger log = LoggerFactory.getLogger(AsyncMailer.class);

  private JavaMailSender mailSender;

  public AsyncMailer(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Async
  @Override
  public void send(Message message) {
    Assert.notNull(message, "`message` 파라미터는 null이면 안됩니다.");

    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();

      if (StringUtils.isNotBlank(message.getFrom())) {
        mailMessage.setFrom(message.getFrom());
      }
      if (StringUtils.isNotBlank(message.getSubject())) {
        mailMessage.setSubject(message.getSubject());
      }
      if (StringUtils.isNotEmpty(message.getBody())) {
        mailMessage.setText(message.getBody());
      }
      if (message.getTo() != null) {
        mailMessage.setTo(message.getTo());
      }

      mailSender.send(mailMessage);

    } catch (MailException e) {
      log.error("메시지 전송에 실패했습니다. ", e);
    }
  }

}
