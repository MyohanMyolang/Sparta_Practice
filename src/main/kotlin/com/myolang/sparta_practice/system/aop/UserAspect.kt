package com.myolang.sparta_practice.system.aop

import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class UserAspect(
	val request: HttpServletRequest
) {

	private val logger = LoggerFactory.getLogger(this::class.java)

	@Before("execution(* com.myolang.sparta_practice.api.controller.*.*(..))")
	fun requestUserLog(joinPoint: JoinPoint){
		logger.info("remoteAddr :  ${request.remoteAddr} Call ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}")
	}
}