package com.example.springsecuritytutorial.member.application

import com.example.springsecuritytutorial.auth.AuthTokenInfo
import com.example.springsecuritytutorial.auth.JwtTokenProvider
import com.example.springsecuritytutorial.auth.MemberRole
import com.example.springsecuritytutorial.auth.MemberRoleRepository
import com.example.springsecuritytutorial.auth.Role
import com.example.springsecuritytutorial.member.application.dto.LoginCommand
import com.example.springsecuritytutorial.member.application.dto.MemberInfo
import com.example.springsecuritytutorial.member.application.dto.SignUpCommand
import com.example.springsecuritytutorial.member.domain.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    fun signUp(command: SignUpCommand): MemberInfo {
        validateLoginId(command.loginId)

        val member = command.toMemberEntity()
        val savedMember = memberRepository.save(member)

        val memberRole = MemberRole(Role.MEMBER, savedMember.id)
        memberRoleRepository.save(memberRole)

        return MemberInfo.from(savedMember)
    }

    private fun validateLoginId(loginId: String) {
        val member = memberRepository.findByLoginId(loginId)
        if (member != null) {
            throw RuntimeException("이미 등록된 ID 입니다.")
        }
    }

    fun login(command: LoginCommand): AuthTokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(command.loginId, command.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return AuthTokenInfo.from(jwtTokenProvider.createToken(authentication))
    }

    fun getMyInfo(id: Long): MemberInfo {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw RuntimeException("회원번호(${id})가 존재하지 않는 유저입니다.")
        return MemberInfo.from(member)
    }
}
