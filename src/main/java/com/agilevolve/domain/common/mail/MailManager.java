package com.agilevolve.domain.common.mail;

public interface MailManager {
  /**
   * 회원가입자에게 메일을 보냅니다.
   *
   * @param emailAddress 회원가입자의 이메일
   * @param subject      이메일 제목
   * @param template     이메일 템플릿 파일
   * @param variables    템플릿 파일의 메시지 가변 인자
   */
  void send(String emailAddress, String subject, String template, MessageVariable... variables);
}
