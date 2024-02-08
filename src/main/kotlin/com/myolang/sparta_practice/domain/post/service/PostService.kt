package com.myolang.sparta_practice.domain.post.service

import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import com.myolang.sparta_practice.domain.post.dto.AddPostReq
import com.myolang.sparta_practice.domain.post.dto.SearchPostReq
import com.myolang.sparta_practice.domain.post.entity.PostEntity
import com.myolang.sparta_practice.domain.post.repository.IPostRepository
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import org.springframework.stereotype.Service

@Service
class PostService(
	private val postRepository: IPostRepository
) {
	fun addPost(member: MemberEntity, dto: AddPostReq) =
		PostEntity.of(dto = dto, member = member)
			.let { postRepository.addPost(it) }
			.toResponse()

	fun searchPost(dto: SearchPostReq) =
		postRepository.searchPostListByTitleOrTagListOrDescription(
			title = dto.title,
			tagList = dto.tagList,
			description = dto.description
		).map { it.toResponse() }

	fun getPostByPostId(postId: Long) =
		postRepository.getPostByPostId(postId)?.toResponse()
			?: throw NotFoundTargetException(message = "$postId Post가 존재하지 않습니다.")
}