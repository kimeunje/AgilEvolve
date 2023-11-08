package com.taskagile.web.results;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.taskagile.domain.model.team.Team;

public class CreateTeamResult {

  private final static Logger log = LoggerFactory.getLogger(CreateTeamResult.class);

  public static ResponseEntity<ApiResult> build(Team team) {
    log.debug("팀이 생성 완료되었습니다." + team);
    ApiResult apiResult = ApiResult.blank()
        .add("id", team.getId().value())
        .add("name", team.getName());
    return Result.ok(apiResult);
  }
}
