package com.example.springsecuritytutorial.auth

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.boot.autoconfigure.security.servlet.PathRequest.H2ConsoleRequestMatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {

    companion object {
        val WHITE_LIST = arrayOf("/api/member/signup")
    }

    @Bean
    fun mvcRequestMatchBuilder(introspector: HandlerMappingIntrospector): MvcRequestMatcher.Builder {
        return MvcRequestMatcher.Builder(introspector)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, mvcRequestMatcherBuilder: MvcRequestMatcher.Builder): SecurityFilterChain {
        http.httpBasic { it.disable() }
            .csrf {
                it.disable()
//                it.ignoringRequestMatchers(PathRequest.toH2Console())
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(*createMvcRequestMatcherForWhiteList(mvcRequestMatcherBuilder)).permitAll()
                    .requestMatchers(PathRequest.toH2Console())
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .headers { it.frameOptions{ option -> option.disable() } }
            .addFilterBefore( // 앞에 필터가 돌면 뒤에 필터는 돌지 않음. 앞에 필터만 돔.
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    private fun createMvcRequestMatcherForWhiteList(mvc: MvcRequestMatcher.Builder) =
        (WHITE_LIST.map { mvc.pattern(it) }).toTypedArray()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}