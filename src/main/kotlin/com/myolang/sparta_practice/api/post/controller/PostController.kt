package com.myolang.sparta_practice.api.post.controller

import com.myolang.sparta_practice.api.post.service.PostApiService
import com.myolang.sparta_practice.api.util.responseEntity
import com.myolang.sparta_practice.domain.post.dto.AddPostReq
import com.myolang.sparta_practice.domain.post.dto.SearchPostReq
import com.myolang.sparta_practice.infra.security.UserPrincipal
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
	private val postApiService: PostApiService,
) {

	@PostMapping
	fun addPost(
		@AuthenticationPrincipal userPrincipal: UserPrincipal,
		@RequestBody @Valid addPostReq: AddPostReq
	) = responseEntity(HttpStatus.CREATED) {
		postApiService.addPost(userPrincipal, addPostReq)
	}

	@GetMapping("/search")
	fun searchPost(@RequestBody @Valid searchPostReq: SearchPostReq) = responseEntity(HttpStatus.OK) {
		postApiService.searchPost(searchPostReq)
	}

	@GetMapping("/{postId}")
	fun getPost(@PathVariable postId: Long) = responseEntity(HttpStatus.OK) {
		postApiService.getPostByPostId(postId)
	}
}