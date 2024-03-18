package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;

public interface TeamService {

  /**
   * 사용자가 만든 팀을 찾습니다.
   *
   * @param userId - 사용자의 id
   * @return 팀 목록 또는 빈 목록(찾을 수 없는 경우)
   */
  List<Team> findTeamsByUserId(UserId userId);

  /**
   * 팀 id로 팀 찾기
   * 
   * @param teamId 팀 id
   * @return 팀 인스턴스 또는 빈 목록(찾을 수 없는 경우)
   */
  Team findById(TeamId teamId);

  /**
   * 새 팀 만들기
   *
   * @param command CreateTeamCommand 인스턴스
   * @return 방금 생성된 새 팀
   */
  Team createTeam(CreateTeamCommand command);
}
