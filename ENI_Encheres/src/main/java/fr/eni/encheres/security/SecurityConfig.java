package fr.eni.encheres.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain; 


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	protected final Log logger = LogFactory.getLog(getClass()); 
	
	@Bean
	SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {auth
			.requestMatchers(HttpMethod.GET, "/encheres").permitAll()
			.requestMatchers(HttpMethod.GET, "/").permitAll()
			.requestMatchers(HttpMethod.GET, "/login").permitAll()
			.requestMatchers(HttpMethod.GET, "/profil").permitAll()
			.requestMatchers(HttpMethod.GET, "/creerarticle").permitAll();
		});
		
		
//		
//		http.formLogin(form -> { 
//
//			form.loginPage("/login").permitAll(); 
//
//			form.defaultSuccessUrl("/"); 
//
//			}); 

			 

			return http.build(); 

			} 
		
	}
	
	

