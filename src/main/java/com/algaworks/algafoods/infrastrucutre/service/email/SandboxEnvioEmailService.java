package com.algaworks.algafoods.infrastrucutre.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafoods.core.email.EmailProperties;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar (Mensagem mensagem) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		try {
			helper.setSubject(mensagem.getAssunto());
			helper.setFrom(emailProperties.getRemetente());
			helper.setText(processarTemplate(mensagem), true);
			helper.setTo(emailProperties.getSandBox().getDestinatario());
			mailSender.send(mimeMessage);
			System.out.println("Enviou para: "+emailProperties.getSandBox().getDestinatario());
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail.",e);
		}
		
			
		}
	

}
