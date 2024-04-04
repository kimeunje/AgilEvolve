package com.agilevolve.domain.application.commands;

import com.agilevolve.domain.model.cardlist.CardListId;

public class ChangeCardListArchivedCommand {

  private CardListId cardListId;
  private boolean archived;

  public ChangeCardListArchivedCommand(CardListId cardListId, boolean archived) {
    this.cardListId = cardListId;
    this.archived = archived;
  }

  public CardListId getCardListId() {
    return cardListId;
  }

  public boolean isArchived() {
    return archived;
  }
}
