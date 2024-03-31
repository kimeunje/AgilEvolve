package com.agilevolve.domain.application;

import java.util.List;

import com.agilevolve.domain.application.commands.AddCardCommand;
import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;

public interface CardService {
  /**
   * 보드의 모든 카드를 검색
   * 
   * @param boardId 보드 id
   * @return 카드 목록들 또는 비어있는 목록(찾을 수 없는 경우)
   */
  List<Card> findByBoardId(BoardId boardId);

  /**
   * 카드 추가
   * 
   * @param command 커멘드 객체
   * @return 새롭게 추가된 카드
   */
  Card addCard(AddCardCommand command);

  /**
   * 카드 포지션 변경
   * 
   * @param command 커멘드 객체
   */
}
