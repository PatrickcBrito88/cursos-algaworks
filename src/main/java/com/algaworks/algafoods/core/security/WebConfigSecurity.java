package com.algaworks.algafoods.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity //Opcional
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	
	//ESTA CLASSE SERVE PARA O BASIC SECURITY
	//PARA UTILIZAR O OAUTH2, COM TOKEN, USAREMOS A RESOURCEWEBSECURITY
	
	//Para usar este projeto como um Resource, tem que incluir a dependência spring-boot-starter-oauth2-resource-server
	
	//Configurando usuário e senha em memória
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("patrick")
					.password(passwordEncoder().encode("123"))
					.roles("ADMIN")
			.and()
				.withUser("thais")
					.password(passwordEncoder().encode("123"))
					.roles("ADMIN");
	}
	
	//Um método que gera encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//vai encriptografar as senhas
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
			.and()
				.authorizeRequests()
					.antMatchers("/cozinhas/**").permitAll()//permita sem requisição
					.anyRequest().authenticated()//Autoriza quem está autenticado
			.and()
				.sessionManagement() //Gerenciamento da sessão
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//diz que não vai ter session na segurança. Não vai mais ter cookie Diz que vai ter que passar as credenciais todas as vezes
			.and()
				.csrf().disable();//
	}

}
