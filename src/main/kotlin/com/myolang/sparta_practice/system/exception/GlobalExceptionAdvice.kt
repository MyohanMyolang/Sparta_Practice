package com.myolang.sparta_practice.system.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

	@ExceptionHandler(CustomException::class)
	fun customExceptionHandler(e: CustomException) = e.errorHandling()


	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun dtoValidateError(error: MethodArgumentNotValidException): ResponseEntity<ErrorObject> {
		val errorMap = mutableMapOf<String, String>()

		error.bindingResult.fieldErrors.forEach {
			errorMap[it.field] = it.defaultMessage ?: "정의되지 않은 에러"
		}

		val errorObject = ErrorObject(ErrorCode.VALIDATION.code, ErrorCode.VALIDATION.message, payload = errorMap)

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObject)
	}

	@ExceptionHandler
	fun expiredJwtException(e: ExpiredJwtException): ResponseEntity<Any> {
		val errorObject = ErrorObject(ErrorCode.JWT_EXPIRED.code, ErrorCode.JWT_EXPIRED.message)
		return ResponseEntity.status(ErrorCode.JWT_EXPIRED.httpStatus).body(errorObject)
	}

	@ExceptionHandler
	fun malformedJwtException(e: MalformedJwtException): ResponseEntity<Any> {
		val errorObject = ErrorObject(ErrorCode.JWT_MALFORMED.code, ErrorCode.JWT_MALFORMED.message)
		return ResponseEntity.status(ErrorCode.JWT_MALFORMED.httpStatus).body(errorObject)
	}

	@ExceptionHandler
	fun signatureException(e: SignatureException): ResponseEntity<Any> {
		val errorObject = ErrorObject(ErrorCode.JWT_SIGNATURE.code, ErrorCode.JWT_SIGNATURE.message)
		return ResponseEntity.status(ErrorCode.JWT_SIGNATURE.httpStatus).body(errorObject)
	}
}