package com.myolang.sparta_practice.domain.post.dto

import java.time.LocalDateTime

data class PostRes(
	val postId: Long,
	val title: String,
	val tagList: List<String>,
	val description: String,
	val writer: String,
	val createdAt: LocalDateTime
)