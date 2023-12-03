package com.example.springsecuritytutorial.member.domain.repository

import com.example.springsecuritytutorial.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByLoginId(loginId: String): Member?
}
