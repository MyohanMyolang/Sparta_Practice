package com.myolang.sparta_practice.api.post.service

import com.myolang.sparta_practice.domain.member.repository.IMemberRepository
import com.myolang.sparta_practice.domain.post.dto.AddPostReq
import com.myolang.sparta_practice.domain.post.dto.SearchPostReq
import com.myolang.sparta_practice.domain.post.service.PostService
import com.myolang.sparta_practice.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
class PostApiService(
	private val postService: PostService,
	private val memberRepository: IMemberRepository
) {
	fun addPost(userPrincipal: UserPrincipal, addPostReq: AddPostReq) =
		memberRepository.findByEmail(userPrincipal.memberEmail)!!
			.let { postService.addPost(it, addPostReq) }

	fun searchPost(searchPostReq: SearchPostReq) =
		postService.searchPost(searchPostReq)

	fun getPostByPostId(postId: Long) =
		postService.getPostByPostId(postId)
}