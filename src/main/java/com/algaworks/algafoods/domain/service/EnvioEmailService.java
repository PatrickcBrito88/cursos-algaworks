package com.algaworks.algafoods.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Builder
	@Getter
	class Mensagem {
		
		@Singular //Singulariza a coleção
		private Set<String> destinatarios; //Pq Set ? Pq não pode se repetir
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular("variavel")//Especifica o nome em singular. Ele não consegue atribuir sozinho
		private Map<String, Object> variaveis;
		
	}
	
}
