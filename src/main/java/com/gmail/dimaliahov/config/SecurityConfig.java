package com.gmail.dimaliahov.config;

import com.gmail.dimaliahov.security.jwt.JwtConfigurer;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN_ENDPOINT = "/api/admin/**";
	private static final String LOGIN_ENDPOINT = "/api/login";
	private static final String REGISTRATION_ENDPOINT = "/api/registration";
	private static final String STUDENT_ENDPOINT = "/api/student/**";
	private static final String TEACHER_ENDPOINT = "/api/teacher/**";
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public SecurityConfig (JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean () throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.authorizeRequests()
				.antMatchers(LOGIN_ENDPOINT).permitAll()
				.antMatchers(REGISTRATION_ENDPOINT).permitAll()
				.antMatchers(STUDENT_ENDPOINT).hasRole("STUDENT")
				.antMatchers(TEACHER_ENDPOINT).hasRole("TEACHER")
				.antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.apply(new JwtConfigurer(jwtTokenProvider));
	}
}
