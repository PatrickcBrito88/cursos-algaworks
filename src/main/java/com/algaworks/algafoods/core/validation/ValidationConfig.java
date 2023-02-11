package com.algaworks.algafoods.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//Classe que pode utilizar para qualquer projeto
@Configuration
public class ValidationConfig {
	
	@Bean// É uma classe que faz a unificação do Bean Validation com o Spring (Messages e Validation properties
	public LocalValidatorFactoryBean validator (MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
		
		//Este método força utilizar o MessageResources e abstrai o ValidationMessage
	}

}
