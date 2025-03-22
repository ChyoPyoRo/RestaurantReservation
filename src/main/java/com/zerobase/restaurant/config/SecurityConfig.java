package com.zerobase.restaurant.config;

import com.zerobase.restaurant.auth.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)//jwt로 인증해서 csrf 보호기능 제거
                .authorizeHttpRequests(authorize->
                        authorize
                                .requestMatchers("/signup", "signin", "restaurants").permitAll()//해당 url에 관한 요청 허용
                                .anyRequest().authenticated())//이외의 요청에 대해서 인증 실행
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션 유지 x(jwt 인증 방식이여서)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)//앞의 필터를 뒤의 필터 이전에 실행
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //비밀번호 암호화 저장
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
