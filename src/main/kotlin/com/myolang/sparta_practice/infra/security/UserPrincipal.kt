package com.myolang.sparta_practice.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


data class UserPrincipal(
	val memberEmail: String,
	val authorities: Collection<GrantedAuthority>,
	val token: String
) {
	constructor(member: String, roles: Set<String>, token: String) : this(
		member,
		roles.map { SimpleGrantedAuthority("ROLE_$it") },
		token
	)
}