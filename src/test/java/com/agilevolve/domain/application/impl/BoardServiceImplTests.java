package com.agilevolve.domain.application.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.common.event.DomainEventPublisher;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.board.BoardManagement;
import com.agilevolve.domain.model.board.BoardMemberRepository;
import com.agilevolve.domain.model.board.BoardRepository;
import com.agilevolve.domain.model.board.events.BoardCreatedEvent;
import com.agilevolve.domain.model.board.events.BoardMemberAddedEvent;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserFinder;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.domain.model.user.UserNotFoundException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 보드 관련 애플리케이션 서비스 테스트
 *
 * 테스트 메소드는 [작업 단위_테스트 중인 상태_ 예상되는 행동] 명명 규약을 따른다.
 */
public class BoardServiceImplTests {

  @Mock
  private BoardRepository boardRepository;
  @Mock
  private BoardManagement boardManagement;
  @Mock
  private DomainEventPublisher domainEventPublisher;
  @Mock
  private BoardMemberRepository boardMemberRepository;
  @Mock
  private UserFinder userFinder;

  private BoardServiceImpl boardService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    boardService = new BoardServiceImpl(boardManagement, domainEventPublisher, boardRepository, boardMemberRepository,
        userFinder);
  }

  // -------------------------------------------
  // 메서드 findBoardsByMembership()
  // -------------------------------------------

  @Test
  public void findBoardsByMembership_noExistUserId_shouldFail() {
    // Arrange
    UserId noExistUserId = new UserId(999L);
    when(boardRepository.findBoardsByMembership(noExistUserId)).thenReturn(Collections.emptyList());

    // Act
    List<Board> resultBoards = boardService.findBoardsByMembership(noExistUserId);

    // Assert
    assertTrue(resultBoards.isEmpty());
    verify(boardRepository).findBoardsByMembership(noExistUserId);
  }

  @Test
  public void findBoardsByMembership_existUserId_shouldSucceed() {
    // Arrange
    UserId userId = new UserId(111L);
    List<Board> expectedBoards = Arrays.asList(new Board(), new Board());
    when(boardRepository.findBoardsByMembership(userId)).thenReturn(expectedBoards);

    // Act
    List<Board> resultBoards = boardService.findBoardsByMembership(userId);

    // Assert
    assertEquals(expectedBoards, resultBoards);
    verify(boardRepository).findBoardsByMembership(userId);
  }

  // -------------------------------------------
  // 메서드 findById()
  // -------------------------------------------

  @Test
  void findById_nonExistBoardId_shouldFail() {
    // Arrange
    BoardId noExistBoardId = new BoardId(999L);
    when(boardRepository.findById(noExistBoardId)).thenReturn(null);

    // Act
    Board result = boardService.findById(noExistBoardId);

    // Assert
    assertNull(result);
    verify(boardRepository).findById(noExistBoardId);
  }

  @Test
  void findById_existBoardId_shouldSucceed() {
    // Arrange
    BoardId boardId = new BoardId(111L);
    Board board = new Board();
    when(boardRepository.findById(boardId)).thenReturn(board);

    // Act
    Board result = boardService.findById(boardId);

    // Assert
    assertEquals(board, result);
    verify(boardRepository).findById(boardId);
  }

  // -------------------------------------------
  // 메서드 findMembers()
  // -------------------------------------------

  @Test
  void findMembers_nonExistBoardId_shouldFail() {
    // Arrange
    BoardId noExistBoardId = new BoardId(999L);
    when(boardMemberRepository.findMembers(noExistBoardId)).thenReturn(Collections.emptyList());

    // Act
    List<User> result = boardService.findMembers(noExistBoardId);

    // Assert
    assertNotNull(result);
    verify(boardMemberRepository).findMembers(noExistBoardId);
  }

  @Test
  void findMembers_existBoardId_shouldSucceed() {
    // Arrange
    BoardId boardId = new BoardId(111L);
    List<User> expectedUsers = Arrays.asList(new User(), new User());
    when(boardMemberRepository.findMembers(boardId)).thenReturn(expectedUsers);

    // Act
    List<User> result = boardService.findMembers(boardId);

    // Assert
    assertEquals(expectedUsers, result);
    verify(boardMemberRepository).findMembers(boardId);
  }

  // -------------------------------------------
  // 메서드 createBoard()
  // -------------------------------------------

  @Test
  void createBoard_nullCommand_shouldFail() {
    // Arrange
    CreateBoardCommand command = null;

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
      boardService.createBoard(command);
    });
    verify(boardManagement, never()).createBoard(any(), any(), any(), any());
    verify(domainEventPublisher, never()).publish(any());
  }

  @Test
  void createBoard_validCommand_shouldSucceed() {
    // Arrange
    UserId userId = new UserId(111L);
    String name = "Board Name";
    String description = "Board Description";
    TeamId teamId = new TeamId(333L);
    CreateBoardCommand command = new CreateBoardCommand(userId, name, description, teamId);

    Board expectedBoard = new Board();
    when(boardManagement.createBoard(userId, name, description, teamId)).thenReturn(expectedBoard);

    // Act
    Board result = boardService.createBoard(command);

    // Assert
    assertEquals(expectedBoard, result);
    ArgumentCaptor<BoardCreatedEvent> eventCaptor = ArgumentCaptor.forClass(BoardCreatedEvent.class);
    verify(domainEventPublisher).publish(eventCaptor.capture());
    BoardCreatedEvent publishedEvent = eventCaptor.getValue();
    assertEquals(boardService, publishedEvent.getSource());
    assertEquals(expectedBoard, publishedEvent.getBoard());
  }

  // -------------------------------------------
  // 메서드 addMember()
  // -------------------------------------------

  @Test
  void addMember_nonExistUser_shouldFail() throws UserNotFoundException {
    // Arrange
    BoardId boardId = new BoardId(999L);
    String usernameOrEmailAddress = "nonExistUser";

    when(userFinder.find(usernameOrEmailAddress)).thenThrow(new UserNotFoundException());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> {
      boardService.addMember(boardId, usernameOrEmailAddress);
    });
    verify(boardMemberRepository, never()).add(any(), any());
    verify(domainEventPublisher, never()).publish(any());
  }

  @Test
  void addMember_existUser_shouldSucceed() throws UserNotFoundException, IllegalAccessException {
    // Arrange
    BoardId boardId = new BoardId(111L);
    String usernameOrEmailAddress = "sunny";
    User user = new User();
    FieldUtils.writeField(user, "id", 1L, true);

    when(userFinder.find(usernameOrEmailAddress)).thenReturn(user);

    doNothing().when(boardMemberRepository).add(any(BoardId.class), any(UserId.class));

    // Act
    User result = boardService.addMember(boardId, usernameOrEmailAddress);

    // Assert
    assertEquals(user, result);
    verify(boardMemberRepository, times(1)).add(boardId, user.getId());
    verify(domainEventPublisher, times(1)).publish(any(BoardMemberAddedEvent.class));
  }
}
