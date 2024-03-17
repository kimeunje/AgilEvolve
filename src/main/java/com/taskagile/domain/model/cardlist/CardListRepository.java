package com.taskagile.domain.model.cardlist;

import java.util.List;

import com.taskagile.domain.model.board.BoardId;

public interface CardListRepository {

    /**
     * 보드의 카드 리스트들 찾기
     * 
     * @param boardId 보드의 id
     * @return 보드에 존재하는 카드 리스트들의 리스트 또는 비어있는 리스트(찾지 못한 경우)
     */
    List<CardList> findByBoardId(BoardId boardId);

    /**
     * 카드 리스트 저장하기
     * 
     * @param cardList 저장할 카드리스트
     */
    void save(CardList cardList);
}
