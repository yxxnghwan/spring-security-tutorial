package com.example.springsecuritytutorial.member.application.dto

import com.example.springsecuritytutorial.member.domain.Gender
import com.example.springsecuritytutorial.member.domain.entity.Member
import java.time.LocalDate

class SignUpCommand(
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
) {

    fun toMemberEntity(): Member {
        return Member(loginId, password, name, birthDate, gender, email)
    }
}