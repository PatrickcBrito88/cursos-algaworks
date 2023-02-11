package com.algaworks.algafoods.infrastrucutre.service.email;

import com.algaworks.algafoods.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{

	@Override
	public void enviar(Mensagem mensagem) {
		//Foi necessáro alterar o modificador de acesso do método processarTemplate
		//da classe pai para "protected" para poder chamar aqui
		String corpo = processarTemplate(mensagem);
		
		log.info("[FAKE EMAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
		
	}

}
