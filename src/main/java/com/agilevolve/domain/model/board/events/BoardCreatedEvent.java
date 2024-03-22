package com.agilevolve.domain.model.board.events;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.board.Board;

public class BoardCreatedEvent extends DomainEvent {

  private static final long serialVersionUID = 4125633335L;

  private Board board;

  public BoardCreatedEvent(Object source, Board board) {
    super(source);
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }
}
