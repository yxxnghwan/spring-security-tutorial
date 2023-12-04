package com.example.springsecuritytutorial.auth

class AuthToken(
    val grantType: String,
    val accessToken: String,
) {
}