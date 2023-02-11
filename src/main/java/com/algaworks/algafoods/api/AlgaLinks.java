package com.algaworks.algafoods.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.controller.CidadeController;
import com.algaworks.algafoods.api.controller.CozinhaController;
import com.algaworks.algafoods.api.controller.EstadoController;
import com.algaworks.algafoods.api.controller.FluxoPedidoController;
import com.algaworks.algafoods.api.controller.FormaPagamentoController;
import com.algaworks.algafoods.api.controller.PedidoController;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafoods.api.controller.RestauranteProdutoController;
import com.algaworks.algafoods.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafoods.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafoods.api.controller.UsuarioControler;
import com.algaworks.algafoods.api.controller.UsuariosGruposController;



@Component
public class AlgaLinks {
	// Classe Responsável por gerar Links

	// Adicionando TemplateVariables
	private static final TemplateVariables PAGEVARIABLES = new TemplateVariables(
			// TemplateVariables é como se fosse uma lista de TemplateVariable
			new TemplateVariable("page", VariableType.REQUEST_PARAM), // Porque é um parâmetro de requisição
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	private static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	
//	private static final TemplateVariables INCLUIR_INATIVOS = new TemplateVariables(
//			new TemplateVariable("incluirInativos", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos(String rel) {

		TemplateVariables filtrosVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM), // Porque é um parâmetro de
																					// requisição
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)

		);

		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

		return Link.of(UriTemplate.of(pedidosUrl, PAGEVARIABLES.concat(filtrosVariables)), rel);// Pedidos é a String
																								// Rel (tipo
		// withSelfRel)
	}

	// ------ Criando o Link para pedidos

	public Link linkToConfirmacaoPedido(String codigo, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigo)).withRel(rel);
		/*
		 * O método fluxopedidocontroler.confirmar estava como void, mas void não dá
		 * retorno e o Linkto precisa de um retorno. A solução foi colocar
		 * ResponseEntity<Void> na assinatura de todos os métodos
		 */

	}

	public Link linkToCancelarPedido(String codigo, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigo)).withRel(rel);
	}

	public Link linkToEntregarPedido(String codigo, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).entregar(codigo)).withRel(rel);
	}

	// RESTAURANTES

	public Link linkToRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantes(String rel) {
		// Para passar o parâmetro junto no link
		String restauranteUri = linkTo(RestauranteController.class).toUri().toString();

		return Link.of(UriTemplate.of(restauranteUri, PROJECAO_VARIABLES), rel);
	}
	
	
	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteResponsavel(Long restauranteId) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listaResponsaveis(restauranteId))
				.withSelfRel();
	}

	public Link linkToRestauranteAtivar(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativar(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteAbrir(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechar(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
	}
	
	//RESTAURANTE - FORMAS PAGAMENTO 
	
	public Link linkToRestaurantesFormasPagamento(Long restauranteId) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.listar(restauranteId)).withSelfRel();
	}
	
	public Link linkToRestauranteFormaPagamentoDesassociacao(
			Long restauranteId, Long formaPagamentoId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.desassociar(restauranteId, formaPagamentoId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormaPagamentoAssociacao(
			Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.associar(restauranteId, null)).withRel(rel);
	}
	
	//RESTAURANTES - USUÁRIOS
	
	public Link linkToRestauranteUsuarioAssociacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.associar(restauranteId,null)).withRel(rel);
	}
	
	public Link linkToRestauranteUsuarioDesassociacao(Long restauranteId, Long usuarioId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.desassociar(restauranteId, usuarioId)).withRel(rel);
	}
	

	// USUÁRIOS

	public Link linkToUsuarios(Long clienteId, String rel) {
		return linkTo(methodOn(UsuarioControler.class).buscar(clienteId)).withRel(rel);
	}

	public Link linkToUsuarios(Long clienteId) {
		return linkToUsuarios(clienteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarios(String rel) {
		return linkTo(UsuarioControler.class).withRel(rel);
	}

	public Link linkToUsuaios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoUsuarios(Long clienteId, String rel) {
		return linkTo(methodOn(UsuariosGruposController.class).listarGrupos(clienteId)).withRel(rel);
	}

	public Link linkToGrupoUsuarios(Long clienteId) {
		return linkToGrupoUsuarios(clienteId, IanaLinkRelations.SELF.value());
	}

	// FORMA PAGAMENTO

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkTo(methodOn(FormaPagamentoController.class).buscarComDeepTag(formaPagamentoId, null)).withSelfRel();
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class).buscarComDeepTag(formaPagamentoId, null)).withRel(rel);
	}
	
	public Link linkToFormaPagamento() {
		return linkTo(FormaPagamentoController.class).withSelfRel();
	}
	
	public Link linkToFormaPagamentos(String rel){
		return linkTo(FormaPagamentoController.class).withRel(rel);
	}

	// CIDADE

	public Link linkToCidade(Long cidadeId) {
		return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
	}

	public Link linkToCidade(Long cidadeId, String rel) {
		return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withRel(rel);
	}

	public Link linkToCidade(String rel) {
		return linkTo(CidadeController.class).withRel(rel);
	}

	public Link linkToCidades() {
		return linkToCidade(IanaLinkRelations.SELF.value());
	}

	// ESTADO

	public Link linkToEstado(Long estadoId, String rel) {
		return linkTo(methodOn(EstadoController.class).buscar(estadoId)).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(String rel) {
		return linkTo(methodOn(EstadoController.class)).withRel(rel);
	}

	public Link linkToEstados() {
		return linkTo(EstadoController.class).withRel(IanaLinkRelations.SELF.value());
	}

	// COZINHAS

	public Link linkToCozinha(String rel) {
		return linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToCozinha(Long id, String rel) {
		return linkTo(methodOn(CozinhaController.class).buscar(id)).withRel(rel);
	}

	public Link linkToCozinha(Long id) {
		return linkTo(methodOn(CozinhaController.class).buscar(id)).withSelfRel();
	}
	
	//PRODUTOS
	
	public Link linkToProdutos(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class)
				.listar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToProdutos(Long id) {
		return linkToProdutos(id, IanaLinkRelations.SELF.value());
	}
	
//	FOTOS
	public Link linkToFotos(Long produtoId, Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteProdutoFotoController.class)
				.buscar(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToFotos(Long produtoId, Long restauranteId) {
		return linkTo(methodOn(RestauranteProdutoFotoController.class)
				.buscar(restauranteId, produtoId)).withRel(IanaLinkRelations.SELF.value());
	}
	

}
