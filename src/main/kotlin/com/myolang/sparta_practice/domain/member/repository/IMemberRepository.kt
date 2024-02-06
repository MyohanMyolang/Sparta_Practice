package com.myolang.sparta_practice.domain.member.repository

import com.myolang.sparta_practice.domain.member.entity.MemberEntity

interface IMemberRepository {
	fun findByEmail(email: String): MemberEntity?
	fun delete(entity: MemberEntity)
	fun addMember(entity: MemberEntity): MemberEntity
}