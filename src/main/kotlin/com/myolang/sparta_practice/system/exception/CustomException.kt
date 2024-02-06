package com.myolang.sparta_practice.system.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity

abstract class CustomException(
	override val message: String? = null,
	private val errorCode: ErrorCode,
	val payload: Any? = null
) : RuntimeException() {

	open val logger: Logger = LoggerFactory.getLogger(this::class.java)

	abstract fun log()

	/**
	 *
	 */
	fun errorHandling() =
		ErrorObject(
			message = message?.let { message } ?: errorCode.message,
			errorCode = errorCode.code,
			payload = payload
		)
			.also { log() }
			.let { ResponseEntity.status(errorCode.httpStatus).body(it) }

}