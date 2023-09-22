package com.taskagile.domain.common.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DefaultMailManager implements MailManager {

  private final static Logger log = LoggerFactory.getLogger(DefaultMailManager.class);

  private String mailFrom;
  private Mailer mailer;
  private Configuration configuration;

  public DefaultMailManager(@Value("${app.mail-from}") String mailFrom, Mailer mailer, Configuration configuration) {
    this.mailFrom = mailFrom;
    this.mailer = mailer;
    this.configuration = configuration;
  }

  @Override
  public void send(String emailAddress, String subject, String template, MessageVariable... variables) {
    Assert.hasText(emailAddress, "`emailAddress` 파라미터는 빈 값이면 안됩니다.");
    Assert.hasText(subject, "`subject` 파라미터는 빈 값이면 안됩니다.");
    Assert.hasText(template, "`template` 파라미터는 빈 값이면 안됩니다.");

    String messageBody = createMessageBody(template, variables);
    Message message = new SimpleMessage(emailAddress, subject, messageBody, mailFrom);
    mailer.send(message);
  }

  private String createMessageBody(String templateName, MessageVariable... variables) {
    try {
      Template template = configuration.getTemplate(templateName);
      Map<String, Object> model = new HashMap<>();
      if (variables != null) {
        for (MessageVariable variable : variables) {
          model.put(variable.getKey(), variable.getValue());
        }
      }
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    } catch (Exception e) {
      log.error("템플릿으로 메시지를 만드는데 실패했습니다. 템플릿: `" + templateName + "`", e);
      return null;
    }
  }
}
