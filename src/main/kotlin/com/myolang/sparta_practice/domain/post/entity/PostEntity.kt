package com.myolang.sparta_practice.domain.post.entity

import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import com.myolang.sparta_practice.domain.post.dto.AddPostReq
import com.myolang.sparta_practice.domain.post.dto.PostRes
import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class PostEntity(
	val title: String,

	val description: String,

	@Type(value = ListArrayType::class)
	@Column(columnDefinition = "text[]")
	val tagList: List<String>,

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	val member: MemberEntity,
) {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null

	@CreatedDate
	val createdAt: LocalDateTime = LocalDateTime.now()

	companion object {
		fun of(dto: AddPostReq, member: MemberEntity) =
			PostEntity(
				title = dto.title,
				description = dto.description,
				tagList = dto.tagList,
				member = member
			)
	}

	fun toResponse() =
		PostRes(
			title = title,
			postId = id!!,
			description = description,
			tagList = tagList,
			writer = member.nickname,
			createdAt = createdAt
		)
}