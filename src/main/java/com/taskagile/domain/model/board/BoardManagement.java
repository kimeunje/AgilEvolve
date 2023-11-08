package com.taskagile.domain.model.board;

import org.springframework.stereotype.Component;

import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;

@Component
public class BoardManagement {

  private BoardRepository boardRepository;
  private BoardMemberRepository boardMemberRepository;

  public BoardManagement(BoardRepository boardRepository, BoardMemberRepository boardMemberRepository) {
    this.boardRepository = boardRepository;
    this.boardMemberRepository = boardMemberRepository;
  }

  /**
   * 보드를 생성한다.
   *
   * @param creatorId   - 이 보드를 생성하는 사용자의 ID
   * @param name        - 보드의 이름
   * @param description - 보드의 설명
   * @param teamId      - 보드가 속할 팀의 ID, Null일 경우 개인 보드이다.
   * @return 새롭게 생성된 보드
   */
  public Board createBoard(UserId creatorId, String name, String description, TeamId teamId) {
    Board board = Board.create(creatorId, name, description, teamId);
    boardRepository.save(board);
    // 생성자를 보드의 멤버로 추가하기
    BoardMember boardMember = BoardMember.create(board.getId(), creatorId);
    boardMemberRepository.save(boardMember);
    return board;
  }
}
