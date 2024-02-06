package com.myolang.sparta_practice.domain.post.entity

import com.myolang.sparta_practice.domain.comment.entity.CommentEntity
import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
class PostEntity(
	val title: String,

	val description: String,

	@Type(value = ListArrayType::class)
	@Column(columnDefinition = "text[]")
	val tagList: List<String>,

	@ManyToOne(fetch = FetchType.LAZY)
	val memberEntity: MemberEntity,

	@ManyToOne(fetch = FetchType.LAZY)
	val commentEntity: CommentEntity,
) {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null
}