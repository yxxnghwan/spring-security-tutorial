package com.example.springsecuritytutorial.member.application

import com.example.springsecuritytutorial.auth.MemberRole
import com.example.springsecuritytutorial.auth.MemberRoleRepository
import com.example.springsecuritytutorial.auth.Role
import com.example.springsecuritytutorial.member.application.dto.MemberInfo
import com.example.springsecuritytutorial.member.application.dto.SignUpCommand
import com.example.springsecuritytutorial.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
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
}
