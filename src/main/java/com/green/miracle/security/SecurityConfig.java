package com.green.miracle.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//springsecurity6 - boot 3.0~
@Configuration
@EnableWebSecurity //해당하는 설정 정보를 알아서 가져와줌
public class SecurityConfig {

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.authorizeHttpRequests(authorize -> authorize
        		.anyRequest().permitAll()
        		);
            
        return http.build();
    }
}