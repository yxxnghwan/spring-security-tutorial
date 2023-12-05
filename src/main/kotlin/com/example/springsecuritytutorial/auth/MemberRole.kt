package com.example.springsecuritytutorial.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MemberRole(
    role: Role,
    memberId: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = role
        private set

    var memberId: Long = memberId
        private set
}