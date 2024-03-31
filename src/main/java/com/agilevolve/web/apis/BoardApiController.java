package com.agilevolve.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.CardService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserNotFoundException;
import com.agilevolve.web.payload.AddBoardMemberPayload;
import com.agilevolve.web.payload.CreateBoardPayload;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.BoardResult;
import com.agilevolve.web.results.CreateBoardResult;
import com.agilevolve.web.results.Result;

@Controller
public class BoardApiController {

  private BoardService boardService;
  private TeamService teamService;
  private CardListService cardListService;
  private CardService cardService;

  public BoardApiController(BoardService boardService, TeamService teamService, CardListService cardListService,
      CardService cardService) {
    this.boardService = boardService;
    this.teamService = teamService;
    this.cardListService = cardListService;
    this.cardService = cardService;
  }

  @PostMapping("/api/boards")
  public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
      @CurrentUser SimpleUser currentUser) {
    Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
    return CreateBoardResult.build(board);
  }

  @GetMapping("/api/boards/{boardId}")
  public ResponseEntity<ApiResult> getBoard(@PathVariable("boardId") long rawBoardId) {
    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);
    if (board == null) {
      return Result.notFound();
    }
    List<User> members = boardService.findMembers(boardId);

    Team team = null;
    if (!board.isPersonal()) {
      team = teamService.findById(board.getTeamId());
    }

    List<CardList> cardLists = cardListService.findByBoardId(boardId);

    List<Card> cards = cardService.findByBoardId(boardId);

    return BoardResult.build(team, board, members, cardLists, cards);
  }

  @PostMapping("/api/boards/{boardId}/members")
  public ResponseEntity<ApiResult> addMember(@PathVariable("boardId") long rawBoardId,
      @RequestBody AddBoardMemberPayload payload) {

    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);

    if (board == null) {
      return Result.notFound();
    }

    try {
      User member = boardService.addMember(boardId, payload.getUsernameOrEmailAddress());

      ApiResult apiResult = ApiResult.blank()
          .add("id", member.getId().value())
          .add("shortName", member.getInitials());
      return Result.ok(apiResult);
    } catch (UserNotFoundException e) {
      return Result.failure("유저를 찾을 수 없습니다.");
    }
  }

}
