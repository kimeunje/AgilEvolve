package com.taskagile.domain.model.board;

import java.util.List;

import com.taskagile.domain.model.user.UserId;

public interface BoardRepository {

  /**
   * 해당 보드를 포함하여 사용자가 회원으로 가입한 보드를 찾습니다.
   * 사용자가 생성했을 뿐만 아니라 가입한 보드를 포함합니다.
   *
   * @param userId 사용자 ID
   * @return 보드 목록 또는 없을 경우 빈 목록
   */
  List<Board> findBoardsByMembership(UserId userId);

  /**
   * 보드 id로 보드 검색
   *
   * @param boardId 보드 id
   * @return 보드 인스턴스 또는 비어있는 값
   */
  Board findById(BoardId boardId);

  /**
   * 보드를 저장합니다.
   *
   * @param board 저장할 보드
   */
  void save(Board board);
}
