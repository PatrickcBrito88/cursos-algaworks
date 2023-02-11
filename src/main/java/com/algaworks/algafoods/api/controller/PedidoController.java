package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.bytecode.enhance.internal.tracker.SimpleFieldTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.PedidoModelAssembler;
import com.algaworks.algafoods.api.assembler.PedidoModelDisassembler;
import com.algaworks.algafoods.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.api.openapi.controller.PedidoControlerOpenApi;
import com.algaworks.algafoods.core.data.PageWrapper;
import com.algaworks.algafoods.core.data.PageableTranslator;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.model.filter.PedidoFilter;
import com.algaworks.algafoods.domain.repository.PedidoRepository;
import com.algaworks.algafoods.domain.service.EmissaoPedidoService;
import com.algaworks.algafoods.infrastrucutre.repository.spec.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(path="/pedidos", produces=MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControlerOpenApi{
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoModelDisassembler pedidoModelDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

							//Utilizando Filter
//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	public MappingJacksonValue listar (@RequestParam(required = false) String campos){
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectModel(pedidos);  
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//			//Filtra campos, quebrando tudo em um array, a cada vírgula		
//			//Atenção para usar o filerOutAllExcept com Array
//			//No Postman coloca um Param com nome camos e no valor coloca por exemplo: codigo,valorTotal
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	//Explicação lá na classe do SpringFoxConfig
		@ApiImplicitParams({
			@ApiImplicitParam(value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name="campos", paramType="query", type="string")
		})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public PagedModel<PedidoResumoModel> pesquisar (@PageableDefault(size=10) Pageable pageable, PedidoFilter pedidoFilter){
		//PedidoFilter especificado lá no Spec
		Pageable pageableTraduzido = traduzirPageable(pageable);
		/*
		 * Terá que criar um wrapper aqui tendo em vista que o pageable traduzido não está sendo passado 
		 * quando do avanço para a próxima página.
		 * QUando chamamos nomerestaurante o novo pageable com o hateoas não consegue 
		 * mais traduzir o pageable. Aula 19.17
		 * Tem que trazer o pageable original.
		 * O wrapper ficou dentro de core/data
		 */
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter),pageableTraduzido);
		
		//Aula 19.17
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);//NEsta linha eu repasso o pageable inicial
		
		PagedModel<PedidoResumoModel> pagedModel = pagedResourcesAssembler
				.toModel(pedidosPage, pedidoResumoModelAssembler);
		
		
		return pagedModel;
		//Para o findAll funcionar com Specification, tem que acrescentar o extends de JPASpecificationExecutor lá no repositório
	}
	
	//Explicação lá na classe do SpringFoxConfig
	@ApiImplicitParams({
		@ApiImplicitParam(value="Nomes das propriedades para filtrar na resposta, separados por vírgula",
				name="campos", paramType="query", type="string")
	})
	@GetMapping("/{codigoPedido}")
	@ResponseStatus(HttpStatus.OK)
	public PedidoModel buscar (@PathVariable String codigoPedido) {
		return pedidoModelAssembler
				.toModel(cadastroPedidoService.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel salvar (@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido pedido = pedidoModelDisassembler.toObjectModel(pedidoInput);
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(1L);
		return pedidoModelAssembler.toModel(cadastroPedidoService.salvar(pedido));
	}
	
	public Pageable traduzirPageable (Pageable pageable) {
		//Aqui iremos traduzir. QUando o consumidor da Api colocar clienteNome no sort. O tradutor 
		//irá converter para cliente.nome
		var mapeamento = ImmutableMap.of(//Tem que colocar todas que deseja que a requisição seja ordenável
				"nomeCliente", "cliente.nome",
				"codigo","codigo",
				"restaurante.nome", "restaurante.nome",
				"valorTotal", "valorTotal"
		);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
	

}
