package com.rp.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	// LDAP or Database
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {

		Function<String, String> passswordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails1 = createNewUser(passswordEncoder, "rp", "dummy");
		UserDetails userDetails2 = createNewUser(passswordEncoder, "Rohit", "Patra");
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(Function<String, String> passswordEncoder, String username, String password) {
		UserDetails userDetails = User.builder().passwordEncoder(passswordEncoder).username(username).password(password)
				.roles("USER", "ADMIN").build();
		return userDetails;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain customFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		httpSecurity.formLogin(withDefaults());
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		return httpSecurity.build();
	}
}
