package com.myolang.sparta_practice.infra.security.config

import com.myolang.sparta_practice.domain.member.auth.jwt.JwtAuthenticationEntryPoint
import com.myolang.sparta_practice.domain.member.auth.jwt.JwtAuthenticationFailureHandler
import com.myolang.sparta_practice.domain.member.auth.jwt.JwtAuthenticationFilter
import com.myolang.sparta_practice.domain.member.auth.jwt.JwtAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
	private val jwtAuthenticationProvider: JwtAuthenticationProvider,
	private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

	@Bean
	fun securityConfig(
		http: HttpSecurity,
		authenticationManager: AuthenticationManager,
		accessDeniedHandler: AccessDeniedHandler,
		jwtAuthenticationFailureHandler: JwtAuthenticationFailureHandler
	): DefaultSecurityFilterChain {
		val jwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManager, jwtAuthenticationFailureHandler)
		return http
			.httpBasic { it.disable() }
			.formLogin { it.disable() }
			.csrf { it.disable() }
			.cors { it.disable() }
			.headers {
				it.frameOptions { frameOptionConfig -> frameOptionConfig.disable() }
			}
			.authorizeHttpRequests {
				it.requestMatchers("/admin").hasRole("ADMIN")
				it.requestMatchers(
					"/auth/**",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/h2-console/**",
					"/error"
				).permitAll()
					.anyRequest().authenticated()
			}
			.addFilterBefore(
				jwtAuthenticationFilter,
				UsernamePasswordAuthenticationFilter::class.java
			)
			.exceptionHandling {
				it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				it.accessDeniedHandler(accessDeniedHandler)
			}
			.build()
	}

	@Bean
	fun authenticationManager(): AuthenticationManager =
		ProviderManager(jwtAuthenticationProvider)

}