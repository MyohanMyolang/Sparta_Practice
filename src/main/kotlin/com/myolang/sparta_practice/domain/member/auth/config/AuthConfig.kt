package com.myolang.sparta_practice.domain.member.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AuthConfig {
	@Bean
	fun bCryptPasswordEncoderConfig() = BCryptPasswordEncoder()
}