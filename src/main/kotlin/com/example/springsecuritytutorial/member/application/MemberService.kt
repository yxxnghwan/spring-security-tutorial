package com.example.springsecuritytutorial.member.application

import com.example.springsecuritytutorial.member.application.dto.MemberInfo
import com.example.springsecuritytutorial.member.application.dto.SignUpCommand
import com.example.springsecuritytutorial.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signUp(command: SignUpCommand): MemberInfo {
        validateLoginId(command.loginId)

        val member = command.toMemberEntity()
        val savedMember = memberRepository.save(member)

        return MemberInfo.from(savedMember)
    }

    private fun validateLoginId(loginId: String) {
        val member = memberRepository.findByLoginId(loginId)
        if (member != null) {
            throw RuntimeException("이미 등록된 ID 입니다.")
        }
    }
}
