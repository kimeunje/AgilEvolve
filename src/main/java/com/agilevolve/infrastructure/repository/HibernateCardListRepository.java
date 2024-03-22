package com.agilevolve.infrastructure.repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.cardlist.CardListRepository;

import jakarta.persistence.EntityManager;

@Repository
public class HibernateCardListRepository extends HibernateSupport<CardList> implements CardListRepository {

    HibernateCardListRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<CardList> findByBoardId(BoardId boardId) {
        String sql = "SELECT cl.* FROM card_list cl WHERE cl.board_id = :boardId";
        NativeQuery<CardList> query = getSession().createNativeQuery(sql, CardList.class);
        query.setParameter("boardId", boardId.value());
        return query.list();
    }
}
