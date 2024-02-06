package com.myolang.sparta_practice.domain.post.repository

import com.myolang.sparta_practice.domain.post.entity.PostEntity

interface IPostRepository {
	fun searchPostListByTitleOrTagListOrDescription(
		title: String,
		tagList: List<String>,
		description: String
	): List<PostEntity>
}