package com.myolang.sparta_practice.domain.member.auth.jwt.token


import com.myolang.sparta_practice.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.io.Serializable

class JwtAuthenticationToken(
	private val principal: UserPrincipal,
) : AbstractAuthenticationToken(principal.authorities), Serializable {

	init {
		super.setAuthenticated(true)
	}

	override fun getPrincipal() = principal
	override fun getCredentials() = null
	override fun isAuthenticated() = true
}