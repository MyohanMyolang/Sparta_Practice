package com.myolang.sparta_practice.infra.postgresql.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository


@Repository
abstract class QueryDslSupport {

	@PersistenceContext
	protected lateinit var entityManager: EntityManager

	protected val queryFactory : JPAQueryFactory
		get(){
			return JPAQueryFactory(entityManager)
		}
}