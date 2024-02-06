package com.myolang.sparta_practice.infra.postgresql.post

import com.myolang.sparta_practice.domain.post.entity.PostEntity
import com.myolang.sparta_practice.domain.post.repository.IPostRepository
import org.springframework.stereotype.Repository

@Repository
class PostgresqlPostRepository(
	private val jpaPostRepository: JpaPostRepository,
	private val queryDslPostRepository: QueryDslPostRepository
) : IPostRepository {
	override fun searchPostListByTitleOrTagListOrDescription(
		title: String,
		tagList: List<String>,
		description: String
	): List<PostEntity> =
		queryDslPostRepository.searchPostListByTitleOrTagListOrDescription(title, tagList, description)

}