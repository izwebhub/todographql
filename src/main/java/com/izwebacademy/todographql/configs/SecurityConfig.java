package com.izwebacademy.todographql.configs;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().disable()
				.csrf().disable()
				
				.authorizeRequests()
				
				.antMatchers("/graphql").permitAll()
				.antMatchers("/graphiql").permitAll()
				.antMatchers("/vendor/**").permitAll()
				
				.antMatchers("/playground/**").permitAll()
				
				.anyRequest().authenticated();
	}
	
	

}