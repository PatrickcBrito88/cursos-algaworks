package com.algaworks.algafoods.infrastrucutre.service.email;


import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafoods.core.email.EmailProperties;
import com.algaworks.algafoods.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

//@Service
public class SmtpEnvioEmailService implements EnvioEmailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired //Tem que fazer a inclusão do Starter direto pelo Spring
	private Configuration freeMakerConfig;
	
	/*
	 * Dicas para criar HTML para e-mails:
	 * https://ajuda.locaweb.com.br/wiki/boas-praticas-de-html-para-email-marketing-ajuda-locaweb/
	 */
	
	
	@Override
	public void enviar (Mensagem mensagem) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		try {
			helper.setSubject(mensagem.getAssunto());
			helper.setFrom(emailProperties.getRemetente());
			helper.setText(processarTemplate(mensagem), true);
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail.",e);
		}
		
			
		}

	protected String processarTemplate (Mensagem mensagem) {
		try {
			Template template = freeMakerConfig
					.getTemplate(mensagem.getCorpo());//O Corpo foi setado com o nome do arquivo.html. Isso passa por parâmetro para essa classe e é setado como template
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
			//Retorna o template criado e as variáveis com Map (Chave, Valor), as quais serão substituídas na composição da mensagem
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail",e);
		}
		
		
	}
		
	}


