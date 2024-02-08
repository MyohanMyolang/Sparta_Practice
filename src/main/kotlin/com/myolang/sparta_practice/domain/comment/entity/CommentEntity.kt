package com.myolang.sparta_practice.domain.comment.entity

import com.myolang.sparta_practice.domain.post.entity.PostEntity
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class CommentEntity(
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	val post: PostEntity
) {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null
}