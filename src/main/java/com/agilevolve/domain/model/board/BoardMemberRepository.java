package com.agilevolve.domain.model.board;

import java.util.List;

import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;

public interface BoardMemberRepository {

  /**
   * 보드의 멤버들을 찾습니다.
   * 
   * @param boardId 보드 id
   * @return 보드 멤버들의 리스트
   */
  List<User> findMembers(BoardId boardId);

  /**
   * 보드 멤버 저장
   *
   * @param boardMember 저장할 보드 멤버
   */
  void save(BoardMember boardMember);

  /**
   * 보드에 유저 추가하기
   * 
   * @param boardId 보드 id
   * @param userId 유저 id
   */
  void add(BoardId boardId, UserId userId);
}
