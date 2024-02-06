package com.myolang.sparta_practice.domain.member.auth.jwt


import com.myolang.sparta_practice.domain.member.auth.jwt.token.JwtPreAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher


class JwtAuthenticationFilter(
	authenticationManager: AuthenticationManager,
	private val jwtAuthenticationFailureHandler: JwtAuthenticationFailureHandler
) : AbstractAuthenticationProcessingFilter(
	OrRequestMatcher(
		AntPathRequestMatcher("/post/**"),
		AntPathRequestMatcher("/comment/**"),
		AntPathRequestMatcher("/member/**"),
		AntPathRequestMatcher("/admin/**")
	), authenticationManager
) {

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		return request.getHeader(HttpHeaders.AUTHORIZATION)
			?.let { this.authenticationManager.authenticate(JwtPreAuthenticationToken(it.split(" ")[1])) }
			?: JwtPreAuthenticationToken("인증 안된 토큰")
	}

	override fun successfulAuthentication(
		request: HttpServletRequest,
		response: HttpServletResponse,
		chain: FilterChain,
		authResult: Authentication
	) {
		SecurityContextHolder.getContext().authentication = authResult
		chain.doFilter(request, response)
	}

	override fun unsuccessfulAuthentication(
		request: HttpServletRequest,
		response: HttpServletResponse,
		failed: AuthenticationException
	) {
		jwtAuthenticationFailureHandler.onAuthenticationFailure(request, response, failed)
	}
}