package com.algaworks.algafoods.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.StatusPedido;
import com.algaworks.algafoods.domain.repository.PedidoRepository;
import com.algaworks.algafoods.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar (String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		//Registro do evento sempre no agreggateRoot
		//Extende a classe 
		//Seta um evento no método confirmar
		//Cria uma classe para representar o evento
		//Chama o save do repositório (tem que chamar, é obrigado)
		
		pedidoRepository.save(pedido);
		
	}
	
	@Transactional
	public void cancelar (String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		pedidoRepository.save(pedido);//Tem que colocar o pedidoRepository para o evento ser executado
	}
	
	@Transactional
	public void entregar (String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
}
