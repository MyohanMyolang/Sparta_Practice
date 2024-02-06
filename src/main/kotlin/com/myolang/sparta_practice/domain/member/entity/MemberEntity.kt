package com.myolang.sparta_practice.domain.member.entity

import com.myolang.sparta_practice.domain.member.dto.SignRequest
import com.myolang.sparta_practice.domain.member.dto.SignupResponse
import com.myolang.sparta_practice.domain.member.type.UserRole
import io.hypersistence.utils.hibernate.type.array.ListArrayType
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "member")
class MemberEntity(

	@Column(name = "email", unique = true)
	var email: String,

	@Column(name = "password")
	var password: String,

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	val role: UserRole = UserRole.ROLE_USER,

	@Type(value = ListArrayType::class)
	@Column(columnDefinition = "text[]")
	var passwordList: List<String>
) {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null

	@Column(name = "refresh")
	var refresh: String? = null

	fun updatePasswordList(passwordList: List<String>) {
		this.passwordList = passwordList
	}

	companion object{
		fun of(dto: SignRequest, role: UserRole) =
			MemberEntity(
				email = dto.email,
				password = dto.password,
				role = role,
				passwordList = listOf(dto.password)
			)
	}
}