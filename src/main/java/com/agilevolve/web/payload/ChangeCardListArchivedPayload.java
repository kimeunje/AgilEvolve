package com.agilevolve.web.payload;

import com.agilevolve.domain.application.commands.ChangeCardListArchivedCommand;
import com.agilevolve.domain.model.cardlist.CardListId;

public class ChangeCardListArchivedPayload {

  private boolean archived;

  public ChangeCardListArchivedCommand toCommand(CardListId cardListId) {
    return new ChangeCardListArchivedCommand(cardListId, archived);
  }

  public boolean isArchived() {
    return archived;
  }

  public void setArchived(boolean archived) {
    this.archived = archived;
  }
}
