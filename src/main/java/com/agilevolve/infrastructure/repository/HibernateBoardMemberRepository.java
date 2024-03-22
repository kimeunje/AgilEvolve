package com.agilevolve.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.agilevolve.domain.model.board.BoardMember;
import com.agilevolve.domain.model.board.BoardMemberRepository;

import jakarta.persistence.EntityManager;

@Repository
public class HibernateBoardMemberRepository extends HibernateSupport<BoardMember> implements BoardMemberRepository {

  HibernateBoardMemberRepository(EntityManager entityManager) {
    super(entityManager);
  }
}
