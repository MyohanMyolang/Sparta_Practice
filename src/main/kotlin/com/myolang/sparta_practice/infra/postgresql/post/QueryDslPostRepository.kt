package com.myolang.sparta_practice.infra.postgresql.post

import com.myolang.sparta_practice.domain.post.entity.PostEntity
import com.myolang.sparta_practice.domain.post.entity.QPostEntity
import com.myolang.sparta_practice.infra.postgresql.querydsl.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.Expressions
import org.springframework.stereotype.Component

@Component
class QueryDslPostRepository : QueryDslSupport() {

	private val post = QPostEntity.postEntity

	fun searchPostListByTitleOrTagListOrDescription(title: String?, tagList: List<String>?, description: String?): MutableList<PostEntity> =
		queryFactory
			.selectFrom(post)
			.also {
				BooleanBuilder().also { builder ->
					title?.let { builder.or(post.title.contains(it)) }
					tagList?.let {
						Expressions.booleanTemplate(
							"ARRAY_POSITION({0}, {1}) IS NOT NULL",
							post.tagList,
							tagList
						).let { builder.or(it) }
					}
					description?.let { builder.or(post.description.contains(it)) }
				}.let { builder -> it.where(builder) }
			}
			.fetch()
}