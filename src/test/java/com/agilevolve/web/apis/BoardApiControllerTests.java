package com.agilevolve.web.apis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.application.commands.CreateBoardCommand;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;
import com.agilevolve.domain.model.user.UserNotFoundException;
import com.agilevolve.web.payload.AddBoardMemberPayload;
import com.agilevolve.web.payload.CreateBoardPayload;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.BoardResult;
import com.agilevolve.web.results.CreateBoardResult;
import com.agilevolve.web.results.Result;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BoardApiControllerTests {

  @Mock
  private BoardService boardService;
  @Mock
  private TeamService teamService;
  @Mock
  private CardListService cardListService;
  @Mock
  private CardService cardService;
  @InjectMocks
  private BoardApiController boardController;

  public SimpleUser currentUser;

  @BeforeEach
  void setUp() throws IllegalAccessException {
    MockitoAnnotations.openMocks(this);
    currentUser = simpleUser();
  }

  @Test
  void createBoard_validPayload_shouldSucceedAndReturn200() throws IllegalAccessException {
    // Arrange
    CreateBoardPayload createBoardPayload = new CreateBoardPayload();
    createBoardPayload.setName("Test Board");
    createBoardPayload.setDescription("Test Description");
    createBoardPayload.setTeamId(1L);

    Board board = Board.create(new UserId(1L), "Test Board", "Test Description", new TeamId(1L));
    FieldUtils.writeField(board, "id", 1L, true);

    when(boardService.createBoard(any(CreateBoardCommand.class))).thenReturn(board);

    // Act
    ResponseEntity<ApiResult> response = boardController.createBoard(createBoardPayload, currentUser);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(CreateBoardResult.build(board), response);
  }

  @Test
  void getBoard_blankPayload_shouldSucceedAndReturn200() throws IllegalAccessException {
    // Arrange
    long rawBoardId = 1L;
    BoardId boardId = new BoardId(rawBoardId);

    Board board = new Board();
    FieldUtils.writeField(board, "id", 1L, true);

    List<User> members = new ArrayList<>();
    Team team = new Team();
    List<CardList> cardLists = new ArrayList<>();
    List<Card> cards = new ArrayList<>();

    when(boardService.findById(boardId)).thenReturn(board);
    when(boardService.findMembers(boardId)).thenReturn(members);
    when(teamService.findById(board.getTeamId())).thenReturn(team);
    when(cardListService.findByBoardId(boardId)).thenReturn(cardLists);
    when(cardService.findByBoardId(boardId)).thenReturn(cards);

    // Act
    ResponseEntity<ApiResult> response = boardController.getBoard(rawBoardId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(BoardResult.build(team, board, members, cardLists, cards), response);
  }

  @Test
  void addMember_nonExistedBoard_shouldFailAndReturn404() throws IllegalAccessException {
    // Arrange
    AddBoardMemberPayload addBoardMemberPayload = new AddBoardMemberPayload();
    addBoardMemberPayload.setUsernameOrEmailAddress("Test Board");

    BoardId boardId = new BoardId(1L);

    when(boardService.findById(boardId)).thenReturn(null);

    // Act
    ResponseEntity<ApiResult> response = boardController.addMember(boardId.value(), addBoardMemberPayload);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(Result.notFound(), response);
  }

  @Test
  void addMember_nonExistedUser_shouldFailAndReturn400() throws IllegalAccessException, UserNotFoundException {
    // Arrange
    AddBoardMemberPayload payload = new AddBoardMemberPayload();
    payload.setUsernameOrEmailAddress("Test Board");

    BoardId boardId = new BoardId(1L);

    Board board = new Board();
    FieldUtils.writeField(board, "id", 1L, true);

    when(boardService.findById(boardId)).thenReturn(board);
    when(boardService.addMember(boardId, payload.getUsernameOrEmailAddress())).thenThrow(UserNotFoundException.class);

    // Act
    ResponseEntity<ApiResult> response = boardController.addMember(boardId.value(), payload);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(Result.failure("유저를 찾을 수 없습니다."), response);
  }

  @Test
  void addMember_existedUsername_shouldSucceedAndReturn200() throws IllegalAccessException, UserNotFoundException {
    // Arrange
    AddBoardMemberPayload payload = new AddBoardMemberPayload();
    payload.setUsernameOrEmailAddress("Test Board");

    BoardId boardId = new BoardId(1L);

    Board board = new Board();
    FieldUtils.writeField(board, "id", 1L, true);

    User user = User.create("sunny", "test@agilevolve.com", "Mypassword!");
    FieldUtils.writeField(user, "id", 1L, true);

    ApiResult apiResult = ApiResult.blank()
        .add("id", user.getId().value())
        .add("shortName", user.getInitials());

    when(boardService.findById(boardId)).thenReturn(board);
    when(boardService.addMember(boardId, payload.getUsernameOrEmailAddress())).thenReturn(user);

    // Act
    ResponseEntity<ApiResult> response = boardController.addMember(boardId.value(), payload);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Result.ok(apiResult), response);
  }

  private SimpleUser simpleUser() throws IllegalAccessException {
    User user = User.create("sunny", "sunny@agilevolve.com", "MyPassword@");
    FieldUtils.writeField(user, "id", 1L, true);

    return new SimpleUser(user);
  }
}
