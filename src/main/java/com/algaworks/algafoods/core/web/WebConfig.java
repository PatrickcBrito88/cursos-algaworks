package com.algaworks.algafoods.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	//Interface do spring mvc que define metodos de callback para configurar o sprngmvc
	//addcorsmapping -> Habilitar o cors globalmente
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
//		registry.addMapping("/admin/**")//Tudo para frente de admin, habilita o cors
//		registry.addMapping("/restaurante/**");//Toda a classe de restaurante habilita o cors
		registry.addMapping("/**")//Habilita o projeto inteiro
			.allowedMethods("*");//Define os métodos aceitos
			//.allowedOrigins("*");//Habilita todas as origins
//			
//			.maxAge(30);//Define o tempo de cache (em segundos)
	}
	
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
		/*
		 * Esse filtro gera um hash e coloca no cabeçalho da resposta
		 * A cada requisição ele compara o hash da resposta coincinde com o e-tag que está na requisição
		 * Se for igual retorna 304 (-e igual) se não for igual ele deixa passar e gera uma nova requisição
		 */
	}
	
}
