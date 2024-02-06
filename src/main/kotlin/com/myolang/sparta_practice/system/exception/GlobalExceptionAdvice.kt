package com.myolang.sparta_practice.system.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

	@ExceptionHandler(CustomException::class)
	fun customExceptionHandler(e: CustomException) = e.errorHandling()
}