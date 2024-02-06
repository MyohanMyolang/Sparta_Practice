package com.myolang.sparta_practice.api.auth.controller

import com.myolang.sparta_practice.api.auth.service.AuthApiService
import com.myolang.sparta_practice.api.util.responseEntity
import com.myolang.sparta_practice.domain.member.dto.SignInResponse
import com.myolang.sparta_practice.domain.member.dto.SignRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/auth")
class AuthController(
	private val authApiService: AuthApiService
) {
	@PostMapping("/signup")
	fun signup(@RequestBody @Valid signRequest: SignRequest) = responseEntity(HttpStatus.CREATED) {
		authApiService.signUp(signRequest)
	}

	@PostMapping("/signin")
	fun signIn(@RequestBody @Valid signRequest: SignRequest): ResponseEntity<SignInResponse> {
		return ResponseEntity.status(HttpStatus.CREATED).body(authApiService.signIn(signRequest))
	}
}