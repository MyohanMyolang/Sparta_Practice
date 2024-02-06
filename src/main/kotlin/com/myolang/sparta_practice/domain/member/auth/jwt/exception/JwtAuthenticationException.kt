package com.myolang.sparta_practice.domain.member.auth.jwt.exception


import org.springframework.security.core.AuthenticationException


class JwtAuthenticationException(val exception: Exception) : AuthenticationException("JwtException", exception) {
}