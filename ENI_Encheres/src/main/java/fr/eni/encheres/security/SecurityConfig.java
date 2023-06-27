package fr.eni.encheres.security;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain; 


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	   @Autowired
	    private DataSource dataSource ;

	    @Autowired
	    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
	        auth.jdbcAuthentication()
	                .dataSource( dataSource )
	                .usersByUsernameQuery( "SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo = ? " )
	                .authoritiesByUsernameQuery( "SELECT ?, 'admin' " )
	                ;
	    }
		
	}
	
	

