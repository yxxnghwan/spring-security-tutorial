package com.example.springsecuritytutorial.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    companion object {
        const val EXPIRATION_MILLISECONDS: Long = 1000L * 60L * 30L
    }

    @Value("\${key.secret}")
    private lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    fun createToken(authentication: Authentication): AuthToken {
        val authorities = authentication.authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        val accessToken = Jwts.builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        return AuthToken("Bearer", accessToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)

        val auth = (claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")) as String

        val authorities = auth.split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
        } catch (e: JwtException) {
            println(e)
            return false
        }
        return true
    }
}
