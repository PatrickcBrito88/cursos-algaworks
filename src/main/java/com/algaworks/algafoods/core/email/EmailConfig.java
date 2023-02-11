package com.algaworks.algafoods.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoods.domain.service.EnvioEmailService;
import com.algaworks.algafoods.infrastrucutre.service.email.FakeEnvioEmailService;
import com.algaworks.algafoods.infrastrucutre.service.email.SandboxEnvioEmailService;
import com.algaworks.algafoods.infrastrucutre.service.email.SmtpEnvioEmailService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Configuration
public class EmailConfig {
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()){//Ele Busca o getIMPL e faz um switch
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			case SANDBOX:
				return new SandboxEnvioEmailService();
			default:
				return null;		
		}
	}

}
