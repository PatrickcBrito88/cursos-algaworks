package com.algaworks.algafoods.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.FormaPagamentoNaoAceitaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.model.ItemPedido;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	
	private static final String MSG_ENTIDADE_EM_USO=
			"O pedido de número %d não podeser excluído pois está em uso.";
	
	private static final String MSG_FORMA_PAGAMENTO_NAO_ACEITA=
			"A forma de pagamento %s não é aceita pelo restaurante de código %d.";

	
	public Pedido buscarOuFalhar (String codigoPedido) {
		Pedido pedido = pedidoRepository.findByCodigo(codigoPedido).
				orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
		return pedido;			
	}
	
	@Transactional
	public Pedido salvar (Pedido pedido) {
		validarPedido(pedido);//Valida o pedido
		validaItens(pedido);//Valida os pedidos e lança o preço unitário no itempedido
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());//Seta o frete
		pedido.calcularValorTotal();//Para acrescentar o frete
		return pedidoRepository.save(pedido);
	}
	
	public void validarPedido(Pedido pedido) {
		//Valida cidade
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEndereco().getCidade().getId());
		
		//Valida usuário
		Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
		
		//Valida Restaurante
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		
		//Valida Forma de pagamento
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		//Seta tudo
		pedido.setCliente(cliente);
		pedido.getEndereco().setCidade(cidade);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		//Valida se o restaurante aceita aquela forma de pagamento
		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("O restaurante de código %d não aceita a forma de pagamento %s.",
					restaurante.getId(),formaPagamento.getDescricao()));
		}
	}
	
	public void validaItens(Pedido pedido) {
		System.out.println(pedido.getItens());
		
		pedido.getItens().forEach(itens -> {
		Produto produto = cadastroProdutoService
				.buscarOuFalhar(itens.getProduto().getId(), pedido.getRestaurante().getId());
	
		itens.setPedido(pedido);
		itens.setProduto(produto);
		itens.setPrecoUnitario(produto.getPreco());
		
		});
	}
	
}
