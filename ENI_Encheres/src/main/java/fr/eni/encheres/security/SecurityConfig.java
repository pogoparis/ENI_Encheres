package fr.eni.encheres.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder() ;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo = ? ")
				.authoritiesByUsernameQuery("SELECT ?, 'admin' ");
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth
					// Permettre aux visiteurs d'accéder à la liste des films
					.requestMatchers(HttpMethod.GET, "/encheres").permitAll()
					// Permettre aux visiteurs d'accéder au détail d'un film
					.requestMatchers(HttpMethod.GET, "/profil").permitAll()
					// Accès à la vue principale
					.requestMatchers(HttpMethod.GET, "/details").permitAll()
					.requestMatchers(HttpMethod.GET, "/recherche").permitAll()
					.requestMatchers(HttpMethod.GET, "/inscription").permitAll()
					.requestMatchers(HttpMethod.POST, "/register").permitAll()
					.requestMatchers(HttpMethod.GET, "/creerarticle").authenticated()
					.requestMatchers(HttpMethod.GET, "/article").permitAll()
					.requestMatchers(HttpMethod.GET, "/error").permitAll()
					.requestMatchers(HttpMethod.POST, "/creationarticle").permitAll()
					.requestMatchers(HttpMethod.POST, "/encherir").permitAll()
					.requestMatchers("/").permitAll()
					
					// Permettre à tous d'afficher correctement les images et CSS
					.requestMatchers("/css/*").permitAll().requestMatchers("/Images/*").permitAll()
					
					// Il faut être connecté pour toutes autres URLs
					.anyRequest().authenticated();
		});
		// formulaire de connexion par défaut
		http.formLogin(Customizer.withDefaults());
	        
	        // Customiser le formulaire
	        http.formLogin(form -> {
	            form.loginPage("/login").permitAll();
	            form.defaultSuccessUrl("/encheres").permitAll();
	            form.failureUrl("/login?error");
	        });

		// /logout --> vider la session et le contexte de sécurité
		http.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll());

		return http.build();

	}

}
