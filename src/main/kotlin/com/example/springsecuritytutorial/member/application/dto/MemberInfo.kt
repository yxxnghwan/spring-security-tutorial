package com.example.springsecuritytutorial.member.application.dto

import com.example.springsecuritytutorial.member.domain.Gender
import com.example.springsecuritytutorial.member.domain.entity.Member
import java.time.LocalDate

class MemberInfo(
    val id: Long,
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
) {

    companion object {
        fun from(entity: Member): MemberInfo {
            return MemberInfo(
                entity.id,
                entity.loginId,
                entity.password,
                entity.name,
                entity.birthDate,
                entity.gender,
                entity.email
            )
        }
    }
}