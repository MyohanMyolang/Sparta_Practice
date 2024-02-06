package com.myolang.sparta_practice.domain.member.auth.jwt

import com.myolang.sparta_practice.domain.member.auth.jwt.exception.JwtAuthenticationException
import com.myolang.sparta_practice.domain.member.auth.jwt.token.JwtAuthenticationToken
import com.myolang.sparta_practice.domain.member.auth.jwt.token.JwtPreAuthenticationToken
import com.myolang.sparta_practice.domain.member.repository.IMemberRepository
import com.myolang.sparta_practice.infra.postgresql.member.JpaMemberRepository
import com.myolang.sparta_practice.infra.security.UserPrincipal
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class JwtAuthenticationProvider(
	private val memberRepository: JpaMemberRepository,
	private val jwtPlugin: JwtPlugin
) : AuthenticationProvider {

	private fun generateAuthenticationToken(principal: UserPrincipal) =
		JwtAuthenticationToken(principal = principal)

	private fun loadUser(userId: String, role: String, token: String) =
		memberRepository.findByEmail(userId)
			?.also { member ->
				member.refresh?.let { refresh ->
					jwtPlugin.validateToken(refresh).getOrElse { throw JwtAuthenticationException(it as Exception) }
				} ?: throw JwtAuthenticationException(NotFoundTargetException()) // TODO: CustomException 만들기
			}
			?.let { UserPrincipal(userId, setOf(role), token) }
			?: throw JwtAuthenticationException(NotFoundTargetException()) // TODO: CustomException 만둘기

	private fun getAuthentication(userId: String, role: String, token: String) =
		generateAuthenticationToken(loadUser(userId, role, token))


	override fun authenticate(authentication: Authentication): Authentication {
		return (authentication.credentials as String)
			.let { jwt ->
				jwtPlugin.validateToken(jwt)
					.getOrElse { throw JwtAuthenticationException(it as Exception) }
					.let { claims ->
						val id = claims.payload.subject
						val role = claims.payload.get("role", String::class.java)
						getAuthentication(id, role, jwt)
					}
			}
	}

	override fun supports(authentication: Class<*>): Boolean =
		JwtPreAuthenticationToken::class.java.isAssignableFrom(authentication)
}