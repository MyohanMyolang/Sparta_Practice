package com.myolang.sparta_practice.system.exception

class NotFoundTargetException : CustomException {

	companion object {
		private val errorCode = ErrorCode.NOTFOUND_ENTITY_ERROR
	}

	constructor() : super(errorCode = errorCode)

	constructor(message: String) : super(message, errorCode)

	constructor(payload: Any): super(errorCode = errorCode, payload = payload)

	constructor(message: String, payload: Any) : super(
		message,
		errorCode,
		payload
	)

	override fun log() {
		super.logger.error("대상 Entity를 찾지 못하였습니다.")
		super.logger.info("payload = $payload")
	}
}