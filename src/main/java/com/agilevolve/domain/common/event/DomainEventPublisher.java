package com.agilevolve.domain.common.event;

public interface DomainEventPublisher {
  /**
   * 이벤트를 발행
   *
   * @param event 발행할 이벤트
   */
  void publish(DomainEvent event);
}
