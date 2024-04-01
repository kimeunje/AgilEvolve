package com.agilevolve.domain.model.cardlist.events;

import org.springframework.util.Assert;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.cardlist.CardList;

public class CardListAddedEvent extends DomainEvent {

  private CardList cardList;

  public CardListAddedEvent(Object source, CardList cardList) {
    super(source);
    Assert.notNull(cardList, "`cardList` 파라미터는 null이면 안됩니다.");
    this.cardList = cardList;
  }

  public CardList getCardList() {
    return cardList;
  }
}
