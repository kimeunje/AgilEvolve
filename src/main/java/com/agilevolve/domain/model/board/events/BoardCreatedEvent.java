package com.agilevolve.domain.model.board.events;

import org.springframework.util.Assert;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.board.Board;

public class BoardCreatedEvent extends DomainEvent {

  private static final long serialVersionUID = 4125633335L;

  private Board board;

  public BoardCreatedEvent(Object source, Board board) {
    super(source);
    Assert.notNull(board, "`board` 파라미터는 null이면 안됩니다.");
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }
}
