package com.algaworks.algafoods.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	//Método que vai produzir uma instância de FilterRegistrationBean
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper){
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos",null));
		//Colocando campos em cima, eu troco o parametro no postman de fields (padrão) para campos
		
		//Eu quero usar o Squiggly nas seguintes URls
		var urlPatterns = Arrays.asList("/pedidos/*","/restaurantes/*");
		
		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setOrder(1);
		filterRegistration.setUrlPatterns(urlPatterns);
		//Significa que sempre uma requisição chegar no http vai passar por esse filtro
		return filterRegistration;
		//tipos de pesquisa: sub* -> pega tudo que tem sub, exemplo subtotal
		//cliente.id, cliente.nome, cliente (vem tudo)
		//cliente[-id] -> vem tudo, menos o Id
		
		// Feito isso basta colocar no params a variável fields e no valor o campo que quer usar para filtrar
	}
}
