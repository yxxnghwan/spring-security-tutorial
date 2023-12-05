package com.example.springsecuritytutorial.auth

import com.example.springsecuritytutorial.member.domain.entity.Member
import com.example.springsecuritytutorial.member.domain.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val memberRoleRepository: MemberRoleRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository.findByLoginId(username)?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("해당 유저는 없습니다.")
    }

    private fun createUserDetails(member: Member): UserDetails {
        val memberRoles = memberRoleRepository.findByMemberId(member.id)
        return CustomUser(
            member.id,
            member.loginId,
            passwordEncoder.encode(member.password),
            memberRoles.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
    }
}