package com.taskagile.domain.model.team;

import java.util.List;

import com.taskagile.domain.model.user.UserId;

public interface TeamRepository {
  /**
   * 사용자가 생성한 팀을 찾습니다.
   *
   * @param userId - 사용자 ID
   * @return 팀 목록 또는 없을 경우 빈 목록
   */
  List<Team> findTeamsByUserId(UserId userId);

  /**
   * 팀을 저장합니다.
   *
   * @param team 저장할 팀
   */
  void save(Team team);
}
