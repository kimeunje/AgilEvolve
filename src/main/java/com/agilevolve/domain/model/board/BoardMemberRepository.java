package com.agilevolve.domain.model.board;

public interface BoardMemberRepository {

  /**
   * 보드 멤버 저장
   *
   * @param boardMember 저장할 보드 멤버
   */
  void save(BoardMember boardMember);
}
