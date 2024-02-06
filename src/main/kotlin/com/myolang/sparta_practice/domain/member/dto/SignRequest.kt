package com.myolang.sparta_practice.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignRequest(
    @field:NotBlank(message = "Email을 입력하여 주십시오.")
    @field:Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "EMail 규칙에 맞지 않습니다.")
    val email: String,

    @field:NotBlank(message = "PASSWORD를 입력하여 주십시오.")
    @field:Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).*$", message = "대문자, 숫자, 특수 문자를 포함 시켜 주십시오.")
    @field:Size(min = 8, max = 15, message = "PASSWORD는 8글자 이상으로 작성되어야 합니다.")
    val password: String
)