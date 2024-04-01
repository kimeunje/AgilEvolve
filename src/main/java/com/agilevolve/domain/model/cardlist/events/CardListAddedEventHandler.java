package com.agilevolve.domain.model.cardlist.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CardListAddedEventHandler {

  private final static Logger log = LoggerFactory.getLogger(CardListAddedEventHandler.class);

  @EventListener(CardListAddedEvent.class)
  public void handleEvent(CardListAddedEvent event) {
    log.debug("`{}` 카드 리스트 추가이벤트 전달",
        event.getCardList().getId().value());
  }
}
