package com.myolang.sparta_practice.domain.member.auth.jwt.token

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtPreAuthenticationToken : AbstractAuthenticationToken {
	private val token: String
	private var authenticated: Boolean

	constructor(token: String) : super(null) {
		this.token = token
		this.authenticated = false
	}

	constructor(token: String, authenticated: Boolean) : super(null) {
		this.token = token
		this.authenticated = authenticated
	}

	override fun getPrincipal() = null
	override fun getCredentials() = token
	override fun isAuthenticated(): Boolean = authenticated
}