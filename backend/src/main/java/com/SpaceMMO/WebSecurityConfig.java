package com.SpaceMMO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
	@Autowired
	private Environment env;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
				.requestMatchers("/public/**").permitAll()
					.requestMatchers("/api/**").access(hasScope("openid"))
					.requestMatchers("/openGameSession").permitAll()
				.anyRequest()
				.authenticated()
			).csrf((csrf) -> csrf
						.ignoringRequestMatchers("/*/**")
				)
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

		return http.build();
	}

}
