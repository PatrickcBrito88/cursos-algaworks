package com.algaworks.algafoods.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass //Anotação do lombok. Transforma a classe em final e adiciona o static ao método
public class ResourceUriHelper {

	public static void addUriResponseHealter (Object resourceId) {
		
	//HATEOAS - Para devoler a URI do recurso que acabou de ser criado
	//Para adicionar o Hateoas tem que incluir pelo Starter
		/*
		 * Quando adicionamos a dependência do HATEOAS, dá conflito com outras 
		 * dependências
		 * Ocorre que o Spring fox adiciona a dependência transitiva do spring-plugin-core 1.2.0
		 *  e o hateoas adiciona a 2.0.0
		 *  1º Passo tirar o 1.2.0
		 *  Se rodar só com isso da outro erro na DocumentationsPluginsManager
		 *  Tudo pq esse método chama as dependências transitivas. 
		 *  O Spring fox está configurado para pegar o spring-plugin-core 1.2.0
		 *  
		 *  Solução: Criação de 2 classes que está na pasta core:springfox
		 *  
		 *  Para criar os links podemos usar diversos formatos Hypermedia. Alguns não tem suporte no Hateoas 
		 *  Utilizaremos o formato Hal
		 *  
		 *  1º Passo na classe CidadeModel extende RepresentationModel
		 */
		
		
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri() //Montando um URi com servletUriComponentBuilder
			.path("/{id}")//Vai atribuir um path ao original 
			.buildAndExpand(resourceId).toUri(); //De onde vai tirar o path ?
	
	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes()).getResponse(); //Pega os atributos da resposta da URI
	
	response.setHeader(HttpHeaders.LOCATION, uri.toString());//Passa a URI para o headers
	
	}

}
