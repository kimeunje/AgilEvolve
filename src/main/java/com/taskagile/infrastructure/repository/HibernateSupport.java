package com.taskagile.infrastructure.repository;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;

/**
 * Hibernate의 Session 객체 생성
 */
abstract class HibernateSupport {

  EntityManager entityManager;

  HibernateSupport(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  Session getSession() {
    return entityManager.unwrap(Session.class);
  }
}
