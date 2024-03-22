package com.agilevolve.domain.common.mail;

public interface Mailer {
  /**
   * 메시지 전송
   *
   * @param message 메시지 인스턴스
   */
  void send(Message message);
}
