package com.myolang.sparta_practice.domain.post.service

import com.myolang.sparta_practice.infra.postgresql.post.PostgresqlPostRepository
import com.myolang.sparta_practice.system.exception.ErrorCode
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class PostServiceTest : DescribeSpec({


    describe("getPost(postId) 요청을 할 때"){
        val postRepository = mockk<PostgresqlPostRepository>()
        val postService = PostService(postRepository = postRepository)

        context("없는 id라면"){
            every { postRepository.getPostByPostId(any()) } returns null

            it("NotFoundTargetException이 발생되어야 한다."){
                val exception = shouldThrow<NotFoundTargetException> { postService.getPostByPostId(0) }
                exception.errorCode shouldBe ErrorCode.NOTFOUND_ENTITY_ERROR
            }
        }
    }
})
