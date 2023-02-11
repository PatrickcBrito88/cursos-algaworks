package com.algaworks.algafoods.api.assembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.PedidoController;
import com.algaworks.algafoods.api.controller.RestauranteController;
import com.algaworks.algafoods.api.controller.UsuarioControler;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel>{
	
	@Autowired
	private AlgaLinks algaLinks;
	
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
		
	}

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModel toModel (Pedido pedido) {
		PedidoResumoModel pedidosModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidosModel);
		
		pedidosModel.add(algaLinks.linkToPedidos("pedidos"));
		
		pedidosModel.getRestaurante().add(algaLinks.linkToRestaurante
				(pedido.getRestaurante().getId()));
		
		pedidosModel.getCliente().add(algaLinks.linkToUsuarios(pedido.getCliente().getId()));
		
		return pedidosModel;
	}
	
	public List<PedidoResumoModel> toCollectModel (Collection<Pedido>pedidos){
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
