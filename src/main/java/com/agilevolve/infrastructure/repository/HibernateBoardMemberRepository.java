package com.agilevolve.infrastructure.repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.board.BoardId;
import com.agilevolve.domain.model.board.BoardMember;
import com.agilevolve.domain.model.board.BoardMemberRepository;
import com.agilevolve.domain.model.user.User;
import com.agilevolve.domain.model.user.UserId;

import jakarta.persistence.EntityManager;

@Repository
public class HibernateBoardMemberRepository extends HibernateSupport<BoardMember> implements BoardMemberRepository {

  HibernateBoardMemberRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<User> findMembers(BoardId boardId) {
    String sql = " SELECT u.* FROM user u " +
        " LEFT JOIN board_member bm ON u.id = bm.user_id " +
        " where bm.board_id = :boardId ";
    NativeQuery<User> query = getSession().createNativeQuery(sql, User.class);
    query.setParameter("boardId", boardId.value());
    return query.list();
  }

  @Override
  public void add(BoardId boardId, UserId userId) {
    String sql = 
    " INSERT IGNORE INTO board_member (board_id, user_id) " +
    " VALUE (:boardId, :userId) ";
    NativeQuery<BoardMember> query = getSession().createNativeQuery(sql, BoardMember.class);
    query.setParameter("boardId", boardId.value());
    query.setParameter("userId", userId.value());
    query.executeUpdate();
  }
}
