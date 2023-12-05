package com.example.springsecuritytutorial.auth

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {

    fun findByMemberId(memberId: Long): List<MemberRole>
}
