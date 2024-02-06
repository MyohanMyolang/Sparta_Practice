package com.myolang.sparta_practice.domain.post.service

import com.myolang.sparta_practice.domain.post.repository.IPostRepository

class PostService(
	private val postRepository: IPostRepository
) {

}