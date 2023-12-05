package com.example.springsecuritytutorial.auth

class AuthTokenInfo(
    val grantType: String,
    val accessToken: String,
) {

    companion object {
        fun from(authToken: AuthToken): AuthTokenInfo {
            return AuthTokenInfo(authToken.grantType, authToken.accessToken)
        }
    }
}
