package com.algaworks.algafoods.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafoods.api.model.FormaPagamentoModel;
import com.algaworks.algafoods.api.openapi.controller.RestauranteFormasPagamentoControlerOpenApi;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.service.CadastroFormaPagamentoService;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path="/restaurante/{restauranteId}/formas-pagamento", produces=MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormasPagamentoControlerOpenApi{

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private FormaPagamentoAssembler formaPagamentoAssembler;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		
		CollectionModel<FormaPagamentoModel> formasPagamento=formaPagamentoAssembler
				.toCollectionModel(restaurante.getFormaPagamento())
					.removeLinks().add(algaLinks.linkToRestaurantesFormasPagamento(restauranteId)); //Adicionado no controlador pq é algo específico
		
		formasPagamento.getContent().forEach(formaPagamento -> {
			formaPagamento.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
					restauranteId, formaPagamento.getId(), "desassociação"))
			.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associação"));
		});
		return formasPagamento;
		
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId,
			@PathVariable Long formaPagamentoId){
		cadastroRestauranteService.removerFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId,
			@PathVariable Long formaPagamentoId){
		cadastroRestauranteService.adicionaFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
}
