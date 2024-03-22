package com.agilevolve.domain.common.mail;

public interface Message {

  /**
   * 메시지의 수신자 주소를 가져옵니다.
   *
   * @return 수신자 이메일 주소
   */
  String getTo();

  /**
   * 메시지의 제목을 가져옵니다.
   *
   * @return 메시지 제목
   */
  String getSubject();

  /**
   * 메시지의 본문을 가져옵니다.
   *
   * @return 메시지 본문
   */
  String getBody();

  /**
   * 메시지의 발신자 주소를 가져옵니다.
   *
   * @return 발신자 이메일 주소
   */
  String getFrom();
}
