package com.agilevolve.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.agilevolve.domain.application.BoardService;
import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.MyDataResult;

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
