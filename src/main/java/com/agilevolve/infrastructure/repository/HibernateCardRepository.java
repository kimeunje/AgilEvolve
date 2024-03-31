package com.agilevolve.infrastructure.repository;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.card.Card;
import com.agilevolve.domain.model.card.CardRepository;

@Repository
public class HibernateCardRepository extends HibernateSupport<Card> implements CardRepository {

  HibernateCardRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Card> findByBoardId(BoardId boardId) {
    String sql = "SELECT c.* FROM card c LEFT JOIN card_list cl ON c.card_list_id = cl.id WHERE cl.board_id = :boardId";
    NativeQuery<Card> query = getSession().createNativeQuery(sql, Card.class);
    query.setParameter("boardId", boardId.value());
    return query.list();
  }
}
