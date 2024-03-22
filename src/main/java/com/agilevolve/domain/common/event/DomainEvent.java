package com.agilevolve.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {
  private static final long serialVersionUID = -1554315671L;

  public DomainEvent(Object source) {
    super(source);
  }

  /**
   * 이벤트가 발생한 시간을 반환
   *
   * @return 이벤트가 발생한 시간
   */
  public long occuredAt() {
    return getTimestamp();
  }
}
