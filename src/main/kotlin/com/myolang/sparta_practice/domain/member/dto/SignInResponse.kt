package com.myolang.sparta_practice.domain.member.dto

data class SignInResponse(
    val email: String,
    val accessToken: String,
    val refreshToken: String
)