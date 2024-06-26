package com.agilevolve.infrastructure.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.query.NativeQuery;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.cardlist.CardList;
import com.agilevolve.domain.model.cardlist.CardListId;
import com.agilevolve.domain.model.cardlist.CardListPosition;
import com.agilevolve.domain.model.cardlist.CardListRepository;

@Repository
public class HibernateCardListRepository extends HibernateSupport<CardList> implements CardListRepository {

  private JdbcTemplate jdbcTemplate;

  HibernateCardListRepository(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
    super(entityManager);
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public CardList findById(CardListId cardListId) {
    return getSession().find(CardList.class, cardListId.value());
  }

  @Override
  public List<CardList> findByBoardId(BoardId boardId) {
    String sql = "SELECT cl.* FROM card_list cl WHERE cl.board_id = :boardId";
    NativeQuery<CardList> query = getSession().createNativeQuery(sql, CardList.class);
    query.setParameter("boardId", boardId.value());
    return query.list();
  }

  @Override
  public void changePositions(final List<CardListPosition> cardListPositions) {
    String sql = " update card_list set `position` = ? where id = ? ";

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(@Nonnull PreparedStatement ps, int i) throws SQLException {
        CardListPosition cardListPosition = cardListPositions.get(i);
        ps.setInt(1, cardListPosition.getPosition());
        ps.setLong(2, cardListPosition.getCardListId().value());
      }

      @Override
      public int getBatchSize() {
        return cardListPositions.size();
      }
    });
  }
}
