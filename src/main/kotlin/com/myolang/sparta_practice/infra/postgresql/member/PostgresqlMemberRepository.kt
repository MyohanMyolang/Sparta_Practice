package com.myolang.sparta_practice.infra.postgresql.member

import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import com.myolang.sparta_practice.domain.member.repository.IMemberRepository
import org.springframework.stereotype.Repository

@Repository
class PostgresqlMemberRepository(
	private val jpaMemberRepository: JpaMemberRepository
) : IMemberRepository {
	override fun findByEmail(email: String) =
		jpaMemberRepository.findByEmail(email)

	override fun delete(entity: MemberEntity) =
		jpaMemberRepository.delete(entity)

	override fun addMember(entity: MemberEntity) =
		jpaMemberRepository.save(entity)
}