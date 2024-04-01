package com.agilevolve.domain.model.card;

import java.util.List;

import com.agilevolve.domain.application.commands.ChangeCardPositionsCommand;
import com.agilevolve.domain.model.board.BoardId;

public interface CardRepository {
  /**
   * 보드의 모든 카드를 검색
   * 
   * @param boardId 보드 id
   * @return 카드 목록들 또는 비어있는 목록(찾을 수 없는 경우)
   */
  List<Card> findByBoardId(BoardId boardId);

  /**
   * 카드 저장
   * 
   * @param card 저장할 카드
   */
  void save(Card card);

  /**
   * 카드 포지션 변경
   * 
   * @param cardPositions 카드 포지션
   */
  void changePositions(List<CardPosition> cardPositions);
}
