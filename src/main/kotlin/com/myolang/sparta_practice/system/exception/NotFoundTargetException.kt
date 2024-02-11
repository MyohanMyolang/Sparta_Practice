package com.myolang.sparta_practice.system.exception

class NotFoundTargetException : CustomException {

    override val errorCode = ErrorCode.NOTFOUND_ENTITY_ERROR

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(payload: Any) : super(payload = payload)

    constructor(message: String, payload: Any) : super(
        message,
        payload
    )

    override fun log() {
        super.logger.error("대상 Entity를 찾지 못하였습니다.")
        message?.let { super.logger.info(it) }
        super.logger.info("payload = $payload")
    }
}