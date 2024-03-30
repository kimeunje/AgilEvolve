package com.agilevolve.domain.application.commands;

import java.util.List;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardListPosition;

public class ChangeCardListPositionsCommand {

  private BoardId boardId;
  private List<CardListPosition> cardListPositions;

  public ChangeCardListPositionsCommand(BoardId boardId, List<CardListPosition> cardListPositions) {
    this.boardId = boardId;
    this.cardListPositions = cardListPositions;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public List<CardListPosition> getCardListPositions() {
    return cardListPositions;
  }

}
