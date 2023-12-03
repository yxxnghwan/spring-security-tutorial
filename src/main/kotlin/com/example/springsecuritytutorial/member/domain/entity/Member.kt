package com.example.springsecuritytutorial.member.domain.entity

import com.example.springsecuritytutorial.member.domain.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    loginId: String,
    password: String,
    name: String,
    birthDate: LocalDate,
    gender: Gender,
    email: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(nullable = false, length = 30, updatable = false)
    var loginId: String = loginId
        private set

    @Column(nullable = false, length = 100)
    var password: String = password
        private set

    @Column(nullable = false, length = 10)
    var name: String = name
        private set

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate: LocalDate = birthDate
        private set

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender = gender
        private set

    @Column(nullable = false, length = 30)
    var email: String = email
        private set
}