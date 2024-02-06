package com.myolang.sparta_practice.system.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: Long, val httpStatus: HttpStatus, val message: String) {
	NOTFOUND_ENTITY_ERROR(10001, HttpStatus.NOT_FOUND, "해당하는 데이터를 찾지 못하였습니다."),
	DUPLICATE_ERROR(10002, HttpStatus.BAD_REQUEST, "이미 존재합니다.")
}
