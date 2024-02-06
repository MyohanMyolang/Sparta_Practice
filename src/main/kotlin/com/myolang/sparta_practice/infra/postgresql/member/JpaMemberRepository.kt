package com.myolang.sparta_practice.infra.postgresql.member

import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface JpaMemberRepository: CrudRepository<MemberEntity, Long> {
	fun findByEmail(email:String): MemberEntity?
}