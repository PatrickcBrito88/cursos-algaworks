package com.algaworks.algafoods.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated//Para valer o notnull do remetente
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	private Implementacao Impl=Implementacao.FAKE;
	
	@NotNull
	private String remetente;
	
	private SandBox sandBox; 
	
	
	public enum Implementacao {
		SMTP, FAKE, SANDBOX;
	}
	
	
}


