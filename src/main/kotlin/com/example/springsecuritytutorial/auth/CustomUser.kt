package com.example.springsecuritytutorial.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val memberId: Long,
    userName: String,
    password: String,
    authorities: Collection<GrantedAuthority>
) : User(userName, password, authorities) {
}
