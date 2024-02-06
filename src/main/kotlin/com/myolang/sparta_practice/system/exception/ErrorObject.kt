package com.myolang.sparta_practice.system.exception

import com.fasterxml.jackson.databind.annotation.JsonNaming

data class ErrorObject(
	val errorCode: Long,
	val message: String,
	val payload: Any?
)