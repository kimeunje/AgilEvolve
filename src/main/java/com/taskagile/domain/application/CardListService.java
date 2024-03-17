package com.taskagile.domain.application;

import java.util.List;

import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;

public interface CardListService {

    /**
     * 보드의 카드 리스트들 찾기
     * 
     * @param boardId 보드 id
     * @return 카드 리스트 인스턴스의 리스트 또는 비어있는 리스트(찾지 못한 경우)
     */
    List<CardList> findByBoardId(BoardId boardId);

    /**
     * 카드 리스트 추가
     * 
     * @param command 커멘드 인스턴스
     * @return 새로 추가된 카드 리스트
     */
    CardList addCardList(AddCardListCommand command);
}
