package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.user.UserId;

public interface BoardService {

  /**
   * 해당 보드를 포함하여 사용자가 멤버로 가입한 보드를 찾습니다.
   * 사용자가 만들거나 가입한 보드를 포함합니다.
   *
   * @param userId 사용자의 ID
   * @return 보드 목록 또는 빈 목록(없는 경우)
   */
  List<Board> findBoardsByMembership(UserId userId);

  /**
   * 새 보드 만들기
   *
   * @param command CreateBoardCommand 인스턴스
   * @return 방금 생성된 새 보드
   */
  Board createBoard(CreateBoardCommand command);
}
