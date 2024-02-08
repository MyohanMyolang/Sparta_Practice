package com.myolang.sparta_practice.domain.member.auth.jwt


import com.myolang.sparta_practice.domain.member.auth.jwt.token.JwtPreAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
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
	), authenticationManager
) {

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		// NOTE: 요청이 Get일 경우에는 Filter를 통과시키기 위하여.
		if (request.method == HttpMethod.GET.name()) return JwtPreAuthenticationToken("비로그인", authenticated = true)

		return request.getHeader(HttpHeaders.AUTHORIZATION)
			?.let { this.authenticationManager.authenticate(JwtPreAuthenticationToken(it.split(" ")[1])) }
			?: JwtPreAuthenticationToken("인증 안된 토큰", authenticated = true)
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