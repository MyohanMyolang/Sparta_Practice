package com.myolang.sparta_practice.domain.post.service

import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import com.myolang.sparta_practice.domain.post.entity.PostEntity
import com.myolang.sparta_practice.infra.postgresql.post.PostgresqlPostRepository
import com.myolang.sparta_practice.system.exception.ErrorCode
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk


class PostServiceTest : DescribeSpec({


	describe("getPost(postId) 요청을 할 때") {
		val postRepository = mockk<PostgresqlPostRepository>()
		val postService = PostService(postRepository = postRepository)

		context("있는 Id라면") {
			val memberEntity = mockk<MemberEntity>()

			every { memberEntity.id } returns 1
			every { memberEntity.nickname } returns "테스트"

			val postId = 1L

			val postEntity = spyk<PostEntity>(
				PostEntity(
					title = "테스트 제목",
					description = "테스트 내용",
					tagList = listOf("태", "그", "리", "스", "트"),
					member = memberEntity
				)
			)
			every { postEntity.id } returns postId

			every { postRepository.getPostByPostId(postId) } returns postEntity

			it("postRepository.getPostByPostId(${postId}) mock Test") {
				val result = postRepository.getPostByPostId(postId)

				result!!.id shouldBe postId
			}

			it("PostId는 ${postId}가 있어야한다.") {
				val result = postService.getPostByPostId(postId)

				result.postId shouldBe postId
			}
		}

		context("없는 id라면") {
			every { postRepository.getPostByPostId(any()) } returns null

			val postId = 1L

			it("NotFoundTargetException이 발생되어야 한다.") {
				val exception = shouldThrow<NotFoundTargetException> { postService.getPostByPostId(postId) }
				exception.errorCode shouldBe ErrorCode.NOTFOUND_ENTITY_ERROR
			}
		}
	}
})
