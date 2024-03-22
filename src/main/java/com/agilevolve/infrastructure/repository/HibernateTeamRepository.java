package com.agilevolve.infrastructure.repository;

import java.util.List;
import jakarta.persistence.EntityManager;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.team.TeamId;
import com.agilevolve.domain.model.team.TeamRepository;
import com.agilevolve.domain.model.user.UserId;

@Repository
public class HibernateTeamRepository extends HibernateSupport<Team> implements TeamRepository {

  public HibernateTeamRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Team> findTeamsByUserId(UserId userId) {
    String sql = " SELECT t.* FROM team t WHERE t.user_id = :userId " +
        " UNION " +
        " ( " +
        " SELECT t.* FROM team t, board b, board_member bm " +
        " WHERE t.id = b.team_id AND bm.board_id = b.id AND bm.user_id = :userId " +
        " ) ";
    NativeQuery<Team> query = getSession().createNativeQuery(sql, Team.class);
    query.setParameter("userId", userId.value());
    return query.list();
  }

  @Override
  public Team findById(TeamId teamId) {
    String sql = "from Team where id = :id";
    Query<Team> query = getSession().createQuery(sql, Team.class);
    query.setParameter("id", teamId.value());
    return query.uniqueResult();
  }
}
