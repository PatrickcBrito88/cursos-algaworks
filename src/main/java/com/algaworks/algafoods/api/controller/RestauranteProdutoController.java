package com.algaworks.algafoods.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafoods.api.assembler.ProdutoModelDisassembler;
import com.algaworks.algafoods.api.model.ProdutoModel;
import com.algaworks.algafoods.api.model.input.ProdutoInput;
import com.algaworks.algafoods.api.openapi.controller.ProdutoControlerOpenApi;
import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.ProdutoRepository;
import com.algaworks.algafoods.domain.service.CadastroProdutoService;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path="/restaurante/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements ProdutoControlerOpenApi{

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoModelDisassembler produtoModelDisassembler;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private AlgaLinks algaLinks;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoModelDisassembler.toObjectModel(produtoInput);
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		produto.setRestaurante(restaurante);
		return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		// Com o requestParam eu consigo parametrizar no post se eu quero incluir os
		// inativos ou não

		List<Produto> lista = new ArrayList<>();

		if (incluirInativos == true) {
			lista = produtoRepository.findTodosByRestaurante(restaurante);
		} else {

			lista = produtoRepository.findAtivosByRestaurante(restaurante);
		}

		return produtoModelAssembler.toCollectionModel(lista)
				.add(algaLinks.linkToProdutos(restauranteId, "produtos."));
	}

	@GetMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		return produtoModelAssembler.toModel(produto);
	}
	
	
	
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		produtoModelDisassembler.copyToObject(produtoInput, produtoAtual);
		
		produtoAtual = cadastroProduto.salvar(produtoAtual);
		
		return produtoModelAssembler.toModel(produtoAtual);
	}

}
