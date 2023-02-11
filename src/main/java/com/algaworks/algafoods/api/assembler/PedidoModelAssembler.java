	package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.CidadeController;
import com.algaworks.algafoods.api.controller.FormaPagamentoController;
import com.algaworks.algafoods.api.controller.PedidoController;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.controller.RestauranteProdutoController;
import com.algaworks.algafoods.api.controller.UsuarioControler;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLink;

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		
		// Adicionando a informação de que a requisição do link é paginada
		pedidoModel.add(algaLink.linkToPedidos("pedidos"));
		
		//Condicional para colocar ou não links de confirmação, cancelamento e entrega de pedidos
		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(algaLink.linkToConfirmacaoPedido(pedido.getCodigo(), "Confirmar"));
		}
		
		if (pedido.podeSerCancelado()) {
			pedidoModel.add(algaLink.linkToCancelarPedido(pedido.getCodigo(), "Cancelar"));
		}
		
		if(pedido.podeSerEntregue()) {
			pedidoModel.add(algaLink.linkToEntregarPedido(pedido.getCodigo(), "Entregar"));
		}
	
		// Adicionando Restaurante
		pedidoModel.getRestaurante()
				.add(algaLink.linkToRestaurante(pedido.getRestaurante().getId()));

		// Adicionando Cliente
		pedidoModel.getCliente()
				.add(algaLink.linkToUsuarios(pedidoModel.getCliente().getId()));

		// Adicionando Forma de Pagamento
		pedidoModel.getFormaPagamento().add(algaLink.linkToFormaPagamento(pedido
				.getFormaPagamento().getId()));

		// Adicionando cidade
		pedidoModel.getEndereco().getCidade()
				.add(algaLink.linkToCidade(pedido.getEndereco().getCidade().getId()));

		// Adicionando para cada item da lista de pedidos
		pedidoModel.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class).buscar(pedido.getRestaurante().getId(),
					item.getProdutoId())).withRel("produto"));
		});
		
		return pedidoModel;
	}

	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities).add(linkTo(PedidoController.class).withSelfRel());
	}
//	public List<PedidoModel> toCollectModel (Collection<Pedido>pedidos){
//		return pedidos.stream()
//				.map(pedido -> toModel(pedido))
//				.collect(Collectors.toList());
//	}
}
