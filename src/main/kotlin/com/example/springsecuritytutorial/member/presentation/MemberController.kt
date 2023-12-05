package com.example.springsecuritytutorial.member.presentation

import com.example.springsecuritytutorial.auth.AuthTokenInfo
import com.example.springsecuritytutorial.member.application.MemberService
import com.example.springsecuritytutorial.member.presentation.dto.LoginRequest
import com.example.springsecuritytutorial.member.presentation.dto.MemberResponse
import com.example.springsecuritytutorial.member.presentation.dto.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<MemberResponse> {
        val memberInfo = memberService.signUp(request.toCommand())
        return ResponseEntity.ok(MemberResponse.from(memberInfo))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthTokenInfo> {
        val authTokenInfo = memberService.login(request.toCommand())
        return ResponseEntity.ok(authTokenInfo)
    }
}
