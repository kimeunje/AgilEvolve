package com.taskagile.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.MyDataResult;

@Controller
public class MeApiController {

  private TeamService teamService;
  private BoardService boardService;

  public MeApiController(TeamService teamService, BoardService boardService) {
    this.teamService = teamService;
    this.boardService = boardService;
  }

  @PostMapping("/api/me")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
    return MyDataResult.build(currentUser, teams, boards);
  }
}
