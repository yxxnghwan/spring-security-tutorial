package com.example.springsecuritytutorial.member.presentation.dto

import com.example.springsecuritytutorial.member.application.dto.SignUpCommand
import com.example.springsecuritytutorial.member.domain.Gender
import java.time.LocalDate

class SignUpRequest(
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
) {

    fun toCommand(): SignUpCommand {
        return SignUpCommand(loginId, password, name, birthDate, gender, email)
    }
}