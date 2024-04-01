package com.agilevolve.domain.model.board.events;

import org.springframework.util.Assert;

import com.agilevolve.domain.common.event.DomainEvent;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.user.User;

public class BoardMemberAddedEvent extends DomainEvent {

  private static final long serialVersionUID = 2596266045L;

  private BoardId boardId;
  private User user;

  public BoardMemberAddedEvent(Object source, BoardId boardId, User user) {
    super(source);
    Assert.notNull(boardId, "`boardId` 파라미터는 null이면 안됩니다.");
    Assert.notNull(user, "`user` 파라미터는 null이면 안됩니다.");
    this.boardId = boardId;
    this.user = user;
  }

  public BoardId getBoardId() {
    return boardId;
  }

  public User getUser() {
    return user;
  }
}
