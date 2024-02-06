package com.myolang.sparta_practice.domain.member.auth.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAuthenticationEntryPoint(
	@Qualifier("handlerExceptionResolver")
	private val resolver: HandlerExceptionResolver
) : AuthenticationEntryPoint {
	override fun commence(
		request: HttpServletRequest,
		response: HttpServletResponse,
		authException: AuthenticationException
	) {
		resolver.resolveException(request, response, null, authException.cause as Exception)
	}
}

@Component
class JwtAuthenticationFailureHandler(
	@Qualifier("handlerExceptionResolver")
	private val resolver: HandlerExceptionResolver
) : AuthenticationFailureHandler {
	override fun onAuthenticationFailure(
		request: HttpServletRequest,
		response: HttpServletResponse,
		exception: AuthenticationException
	) {
		resolver.resolveException(request, response, null, exception.cause as Exception)
	}

}

@Component
class JwtAccessDeniedHandler() : AccessDeniedHandler {
	override fun handle(
		request: HttpServletRequest,
		response: HttpServletResponse,
		accessDeniedException: AccessDeniedException
	) {
		response.status = HttpServletResponse.SC_BAD_REQUEST
	}

}