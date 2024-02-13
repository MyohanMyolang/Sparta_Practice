package com.myolang.sparta_practice.api.post.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.myolang.sparta_practice.api.post.service.PostApiService
import com.myolang.sparta_practice.domain.member.auth.jwt.JwtPlugin
import com.myolang.sparta_practice.domain.post.dto.PostRes
import com.myolang.sparta_practice.system.exception.ErrorCode
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.LocalDateTime

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockKExtension::class)
@WebMvcTest(controllers = [PostController::class])
class PostControllerTest @Autowired constructor(
	private val mockMvc: MockMvc,
) : DescribeSpec() {
	@MockkBean
	private lateinit var postApiService: PostApiService

	init {
		extensions(SpringExtension)

		afterContainer { clearAllMocks() }

		describe("GET /post/{postId}는") {
			context("존재하는 ID를 요청할 때") {
				val postId = 1L

				every { postApiService.getPostByPostId(any()) } returns PostRes(
					postId = postId,
					title = "Test Title",
					tagList = listOf("태", "그", "리", "스", "트"),
					description = "test descript",
					writer = "test writer",
					createdAt = LocalDateTime.now()
				)

				val result = mockMvc.perform(
					get("/post/$postId")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_UTF8)
				).andReturn()

				val content = JSONObject(result.response.contentAsString)

				it("200 status code를 응답해야한다.") {
					result.response.status shouldBe 200
				}
				it("Content는 postId에 맞게 제대로 가져와야 한다.") {
					content.get("id") shouldBe postId
				}
				it("tagList는 배열로 가져와야 하며, 한글이 깨져서는 안된다.") {
					val tagList = content.get("tagList") as JSONArray
					tagList[0] shouldBe "태"
				}
			}

			context("존재하지 않는 ID를 요청할 때") {
				val postId = 0L

				every { postApiService.getPostByPostId(any()) } throws NotFoundTargetException("$postId Post를 찾을 수 없습니다.")

				val result = mockMvc.perform(
					get("/post/${postId}")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
				).andReturn()

				val content = JSONObject(result.response.contentAsString)

				it("NotFoundTargetException을 받환한다.") {
					content.get("errorCode") shouldBe ErrorCode.NOTFOUND_ENTITY_ERROR.code
				}
			}
		}

	}
}

//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockKExtension::class)
//@SpringBootTest
//class PostControllerTest @Autowired constructor(
//	private val mockMvc: MockMvc,
//	private val jwtPlugin: JwtPlugin
//) : DescribeSpec({
//
//	extensions(SpringExtension)
//
//	afterContainer { clearAllMocks() }
//
//val postApiService = mockk<PostApiService>()
//
//	describe("GET /post/{postId}는") {
//		context("존재하는 ID를 요청을 보낼 때") {
//			it("200 status code를 응답해야한다.") {
//				val postId = 1L
//
//				every { postApiService.getPostByPostId(any()) } returns PostRes(
//					postId = postId,
//					title = "Test Title",
//					tagList = listOf("태", "그", "리", "스", "트"),
//					description = "test descript",
//					writer = "test writer",
//					createdAt = LocalDateTime.now()
//				)
//
//				val testValue = postApiService.getPostByPostId(postId)
//				testValue.title shouldBe "Test Title"
//
//				val result = mockMvc.perform(
//					get("/post/$postId")
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON)
//				).andReturn()
//
//				result.response.status shouldBe 200
//
//				val responseDto = jacksonObjectMapper().readValue(
//					result.response.contentAsString,
//					PostRes::class.java
//				)
//
//				responseDto.title shouldBe "Test Title"
//			}
//		}
//	}
//
//})