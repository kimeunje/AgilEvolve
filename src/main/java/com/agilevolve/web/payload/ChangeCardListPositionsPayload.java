package com.agilevolve.web.payload;

import java.util.List;

import com.agilevolve.domain.application.commands.ChangeCardListPositionsCommand;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardListPosition;

public class ChangeCardListPositionsPayload {

  private long boardId;
  private List<CardListPosition> cardListPositions;

  public ChangeCardListPositionsCommand toCommand() {
    return new ChangeCardListPositionsCommand(new BoardId(boardId), cardListPositions);
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }

  public void setCardListPositions(List<CardListPosition> cardListPositions) {
    this.cardListPositions = cardListPositions;
  }

}
