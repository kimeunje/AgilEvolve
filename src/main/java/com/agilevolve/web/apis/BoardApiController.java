package com.agilevolve.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.CardListService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.domain.model.user.User;
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

  public BoardApiController(BoardService boardService, TeamService teamService, CardListService cardListService) {
    this.boardService = boardService;
    this.teamService = teamService;
    this.cardListService = cardListService;
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

    Team team = null;
    if (!board.isPersonal()) {
      team = teamService.findById(board.getTeamId());
    }

    List<CardList> cardLists = cardListService.findByBoardId(boardId);

    return BoardResult.build(team, board, cardLists);
  }

}
