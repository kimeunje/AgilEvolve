package com.agilevolve.domain.application.commands;

import java.util.List;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.CardPosition;

public class ChangeCardPositionsCommand {

  private BoardId boardId;
  private List<CardPosition> cardPositions;

  public ChangeCardPositionsCommand(BoardId boardId, List<CardPosition> cardPositions) {
    this.boardId = boardId;
    this.cardPositions = cardPositions;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public List<CardPosition> getCardPositions() {
    return cardPositions;
  }
}
