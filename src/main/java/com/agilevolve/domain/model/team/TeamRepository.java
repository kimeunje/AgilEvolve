package com.agilevolve.domain.model.team;

import java.util.List;

import com.agilevolve.domain.model.user.UserId;

public interface TeamRepository {
  /**
   * 사용자가 생성한 팀을 찾습니다.
   *
   * @param userId - 사용자 ID
   * @return 팀 목록 또는 없을 경우 빈 목록
   */
  List<Team> findTeamsByUserId(UserId userId);

  /**
   * 팀 id 로 팀 찾기
   * 
   * @param teamId 팀 id
   * @return 팀 인스턴스 또는 빈 목록(못 찾을 경우)
   */
  Team findById(TeamId teamId);

  /**
   * 팀을 저장합니다.
   *
   * @param team 저장할 팀
   */
  void save(Team team);
}
