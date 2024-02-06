package com.myolang.sparta_practice.infra.postgresql.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext


abstract class QueryDslSupport {

	@PersistenceContext
	protected lateinit var entityManager: EntityManager

	protected val queryFactory : JPAQueryFactory = JPAQueryFactory(entityManager)
}