package com.myolang.sparta_practice.api.auth.service

import com.myolang.sparta_practice.domain.member.dto.SignRequest
import com.myolang.sparta_practice.domain.member.service.MemberService
import org.springframework.stereotype.Service

@Service
class AuthApiService(
	private val memberService: MemberService
) {
	fun signUp(signRequest: SignRequest) =
		memberService.signUp(signRequest)
			.let { true }

	fun signIn(signRequest: SignRequest) =
		memberService.signIn(signRequest)
}