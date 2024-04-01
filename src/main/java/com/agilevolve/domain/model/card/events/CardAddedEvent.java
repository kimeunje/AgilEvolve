package com.agilevolve.domain.model.card.events;

import org.springframework.util.Assert;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.card.Card;

public class CardAddedEvent extends DomainEvent {

  private static final long serialVersionUID = 7976531298L;

  private Card card;

  public CardAddedEvent(Object source, Card card) {
    super(source);
    Assert.notNull(card, "`card` 파라미터는 null이면 안됩니다.");
    this.card = card;
  }

  public Card getCard() {
    return card;
  }

}
