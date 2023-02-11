package com.algaworks.algafoods.domain.listerner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafoods.domain.events.PedidoCanceladoEvent;
import com.algaworks.algafoods.domain.events.PedidoConfirmadoEvent;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.service.EnvioEmailService;
import com.algaworks.algafoods.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioEmail;	
	
	//@EventListener//Marca o método como um listener. Sempre que o PedidoConfirmadoEvent for lançado
	@TransactionalEventListener//(phase = TransactionPhase.BEFORE_COMMIT)//*
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		
		/*
		 * Com transactionalEventListener os eventos serão disparados depois que 
		 * as ações forem comitadas
		 * Com o phase=TRansactionPhase.BeforeComit o commit fica atrelado ao envio do e-mail.
		 * Ou seja 
		 * O e-mail só é enviado se o commit for realizado e o commit só não é dado rollback 
		 * se o e-mail for enviado. Assim os dois ficam amarrados. 
		 * O professor não usou, mas eu vou deixar aqui comentado
		 */
		
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
				.corpo("pedido-cancelado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
			
	}
}
