package org.springframework.cluedo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/session/**").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/users/**/friends/**").authenticated()
				.antMatchers("/users/**").hasAnyAuthority("admin")
				.antMatchers("/owners/**").permitAll()	
				.antMatchers("/profile/**").authenticated()
				.antMatchers("/newchat/**").authenticated()	
				.antMatchers("/notifications/**").authenticated()	
				.antMatchers("/chat/**").authenticated()
				.antMatchers("/vets/**").authenticated()
				.antMatchers("/games/**").authenticated()
				.antMatchers("/game/**").authenticated()
				.antMatchers("/achievements/**").authenticated()
				.antMatchers("/myAchievements/**").authenticated()
				.antMatchers("/celd/**").permitAll()
				.antMatchers("/stats/**").authenticated()
				.antMatchers("/global").authenticated()
				.anyRequest().denyAll()
				
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
        		http.cors().and().csrf().disable();
	}

	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
          .dataSource(dataSource)
          .usersByUsernameQuery(
              "select username,password,enabled "
                + "from users "
                + "where username = ?")
              .authoritiesByUsernameQuery(
               "select username,authority "
                + "from users "
                + "where username = ?")
          .passwordEncoder(passwordEncoder());
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


