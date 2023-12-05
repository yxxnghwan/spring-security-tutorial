package com.example.springsecuritytutorial.member.presentation.dto

import com.example.springsecuritytutorial.member.application.dto.LoginCommand
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @JsonProperty("loginId")
    val loginId: String,
    @JsonProperty("password")
    val password: String,
) {

    fun toCommand(): LoginCommand {
        return LoginCommand(loginId, password)
    }
}
