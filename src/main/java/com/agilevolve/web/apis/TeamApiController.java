package com.agilevolve.web.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agilevolve.domain.application.TeamService;
import com.agilevolve.domain.common.security.CurrentUser;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.SimpleUser;
import com.agilevolve.web.payload.CreateTeamPayload;
import com.agilevolve.web.results.ApiResult;
import com.agilevolve.web.results.CreateTeamResult;

@Controller
public class TeamApiController {

  private final static Logger log = LoggerFactory.getLogger(TeamApiController.class);

  private TeamService teamService;

  public TeamApiController(TeamService teamService) {
    this.teamService = teamService;
  }

  @PostMapping("/api/teams")
  public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
      @CurrentUser SimpleUser currentUser) {
    Team team = teamService.createTeam(payload.toCommand(currentUser.getUserId()));

    log.debug("팀 생성 중 입니다.");

    return CreateTeamResult.build(team);
  }
}
