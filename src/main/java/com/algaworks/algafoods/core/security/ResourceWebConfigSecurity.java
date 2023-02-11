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

@Configuration
@EnableWebSecurity //Opcional
public class ResourceWebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	//Para usar este projeto como um Resource, tem que incluir a dependência spring-boot-starter-oauth2-resource-server
	
		
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()			.and()
				.authorizeRequests()
					.anyRequest().authenticated()//Autoriza quem está autenticado
			.and()
				.cors()//Liberando cors para o JavaScript acessar (Aula 22.19)
			.and()
				.oauth2ResourceServer()//Quando faz isso habilita esse projeto a ser um resourceserver
				.opaqueToken();//opakeToken é o tipo de token que é emitido pelo authentication server
		
		//Para configurar a checkagem de tokens que vem da requisição, tem que configurar no application properties
//		spring.security.oauth2.resourceserver.opaquetoken.introspection-uri=
		
	}

}
