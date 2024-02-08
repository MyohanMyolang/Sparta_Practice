package com.myolang.sparta_practice.system.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: Long, val httpStatus: HttpStatus, val message: String) {
	NOTFOUND_ENTITY_ERROR(10001, HttpStatus.NOT_FOUND, "해당하는 데이터를 찾지 못하였습니다."),
	DUPLICATE_ERROR(10002, HttpStatus.BAD_REQUEST, "이미 존재합니다."),
	VALIDATION(10003, HttpStatus.BAD_REQUEST, "Validation을 통과하지 못했습니다."),


	JWT_EXPIRED(5001, HttpStatus.FORBIDDEN, "토큰의 유효시간이 만료되었습니다."),
	JWT_MALFORMED(5002, HttpStatus.BAD_REQUEST, "토큰이 부적절 합니다."),
	JWT_SIGNATURE(5003, HttpStatus.BAD_REQUEST, "서명이 유효하지 않습니다.");
}
