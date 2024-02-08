package com.myolang.sparta_practice.domain.member.service

import com.myolang.sparta_practice.domain.member.auth.dto.UpdateMemberRequest
import com.myolang.sparta_practice.domain.member.auth.jwt.JwtPlugin
import com.myolang.sparta_practice.domain.member.dto.SignInResponse
import com.myolang.sparta_practice.domain.member.dto.SignRequest
import com.myolang.sparta_practice.domain.member.entity.MemberEntity
import com.myolang.sparta_practice.domain.member.repository.IMemberRepository
import com.myolang.sparta_practice.domain.member.type.UserRole
import com.myolang.sparta_practice.infra.security.UserPrincipal
import com.myolang.sparta_practice.system.exception.NotFoundTargetException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
	private val memberRepository: IMemberRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtPlugin: JwtPlugin
) {

	@Transactional
	fun signUp(signRequest: SignRequest) =
		memberRepository.findByEmail(signRequest.email)
			?.let { TODO("이미 존재하는 유저 Exception") }
			?: passwordEncoder.encode(signRequest.password)
				.let { MemberEntity.of(signRequest.copy(password = it), UserRole.ROLE_USER) }
				.let { memberRepository.addMember(it) }


	@Transactional
	fun signIn(signRequest: SignRequest) =
		memberRepository.findByEmail(signRequest.email)
			?.apply { if (!passwordEncoder.matches(signRequest.password, password)) throw TODO("Password불일치") }
			?.apply { refresh = jwtPlugin.generateRefreshToken(email, role.name) }
			?.let {
				SignInResponse(
					email = it.email,
					accessToken = jwtPlugin.generateAccessToken(it.email, role = it.role.name),
					refreshToken = it.refresh!!
				)
			}
			?: TODO("해당하는 Email을 찾을 수 없음")

	/**
	 * 로그인 된 사용자가 Password를 한번 더 입력 받아 확인하는 상황에 사용하는 함수이다.
	 */
	fun checkPassword(userPrincipal: UserPrincipal, password: String) =
		memberRepository.findByEmail(userPrincipal.memberEmail)!!
			.let { passwordEncoder.matches(password, it.password) }

	@Transactional
	fun changeProfile(userPrincipal: UserPrincipal, updateDto: UpdateMemberRequest) {
		memberRepository.findByEmail(userPrincipal.memberEmail)!!
			.run { updatePasswordList(passwordListUpdate(this.passwordList, updateDto.password)) }
	}

	private fun passwordListUpdate(passwordList: List<String>, password: String) =
		passwordList.find { passwordEncoder.matches(password, it) }
			?.let { throw TODO("중복 PassWord") }
			?: let {
				val newPasswordList: MutableList<String> = passwordList.toMutableList()
				newPasswordList.add(passwordEncoder.encode(password))
				if (newPasswordList.size > 3) newPasswordList.removeAt(0)
				newPasswordList
			}

	@Transactional
	fun deleteMember(userPrincipal: UserPrincipal, memberEmail: String) {
		memberRepository.findByEmail(memberEmail)
			?.let { memberRepository.delete(it) }
			?: throw NotFoundTargetException()
	}

	@Transactional
	fun logout(userPrincipal: UserPrincipal) {
		memberRepository.findByEmail(userPrincipal.memberEmail)!!.refresh = null
	}
}