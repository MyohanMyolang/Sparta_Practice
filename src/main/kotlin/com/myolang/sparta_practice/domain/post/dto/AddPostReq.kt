package com.myolang.sparta_practice.domain.post.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddPostReq(
	@field:NotBlank(message = "제목은 필수로 입력되어야 합니다.")
	val title: String,

	@field:NotBlank(message = "내용은 필수로 입력되어야 합니다.")
	val description: String,

	@field:NotNull(message = "tagList는 null이 되어서는 안됩니다.")
	val tagList: List<String>
)
