package com.example.springsecuritytutorial.member.presentation.dto

import com.example.springsecuritytutorial.member.application.dto.MemberInfo
import com.example.springsecuritytutorial.member.domain.Gender
import java.time.LocalDate

class MemberResponse(
    val id: Long,
    val loginId: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
) {

    companion object {
        fun from(info: MemberInfo): MemberResponse {
            return MemberResponse(
                info.id,
                info.loginId,
                info.name,
                info.birthDate,
                info.gender,
                info.email
            )
        }
    }
}