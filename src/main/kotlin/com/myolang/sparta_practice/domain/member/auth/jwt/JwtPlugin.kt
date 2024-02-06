package com.myolang.sparta_practice.domain.member.auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
class JwtPlugin(
	@Value("\${jwt.secret_key}")
	private val secretKey: String
) {

	companion object {
		const val ISSUER = "team.a4.com"
		const val ACCESS_TOKEN_EXPIRATION_HOUR: Long = 1
		const val REFRESH_TOKEN_EXPIRATION_HOUR: Long = 168
	}

	fun validateToken(jwt: String): Result<Jws<Claims>> {
		return kotlin.runCatching {
			val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
			Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
		}
	}

	fun generateAccessToken(subject: String, role: String): String {
		return generateToken(subject, role, Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))
	}

	fun generateRefreshToken(subject: String, role: String): String{
		return generateToken(subject, role, Duration.ofHours(REFRESH_TOKEN_EXPIRATION_HOUR))
	}


	private fun generateToken(subject: String, role: String, expirationPeriod: Duration): String {
		val claims: Claims = Jwts.claims()
			.add(mapOf("role" to role))
			.build()

		val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
		val now = Instant.now()

		return Jwts.builder()
			.subject(subject)
			.issuer(ISSUER)
			.issuedAt(Date.from(now))
			.expiration(Date.from(now.plus(expirationPeriod)))
			.claims(claims)
			.signWith(key)
			.compact()
	}

}
