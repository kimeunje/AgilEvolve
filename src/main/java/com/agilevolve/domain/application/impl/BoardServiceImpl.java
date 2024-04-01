package com.agilevolve.domain.application.impl;

import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.board.BoardManagement;
import com.agilevolve.domain.model.board.BoardMemberRepository;
import com.agilevolve.domain.model.board.BoardRepository;
import com.agilevolve.domain.model.board.events.BoardCreatedEvent;
import com.agilevolve.domain.model.board.events.BoardMemberAddedEvent;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserFinder;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.domain.model.user.UserNotFoundException;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

  private BoardRepository boardRepository;
  private BoardManagement boardManagement;
  private DomainEventPublisher domainEventPublisher;
  private BoardMemberRepository boardMemberRepository;
  private UserFinder userFinder;

  public BoardServiceImpl(BoardManagement boardManagement, DomainEventPublisher domainEventPublisher,
      BoardRepository boardRepository, BoardMemberRepository boardMemberRepository, UserFinder userFinder) {
    this.boardManagement = boardManagement;
    this.domainEventPublisher = domainEventPublisher;
    this.boardRepository = boardRepository;
    this.boardMemberRepository = boardMemberRepository;
    this.userFinder = userFinder;
  }

  @Override
  public List<Board> findBoardsByMembership(UserId userId) {
    return boardRepository.findBoardsByMembership(userId);
  }

  @Override
  public Board findById(BoardId boardId) {
    return boardRepository.findById(boardId);
  }

  @Override
  public List<User> findMembers(BoardId boardId) {
    return boardMemberRepository.findMembers(boardId);
  }

  @Override
  public Board createBoard(CreateBoardCommand command) {
    Assert.notNull(command, "`command` 파라미터는 null이면 안됩니다.");
    Board board = boardManagement.createBoard(command.getUserId(), command.getName(), command.getDescription(),
        command.getTeamId());
    domainEventPublisher.publish(new BoardCreatedEvent(this, board));
    return board;
  }

  @Override
  public User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException {
    User user = userFinder.find(usernameOrEmailAddress);
    boardMemberRepository.add(boardId, user.getId());
    domainEventPublisher.publish(new BoardMemberAddedEvent(this, boardId, user));
    return user;
  }
}
