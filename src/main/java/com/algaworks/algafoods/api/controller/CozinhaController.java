package com.algaworks.algafoods.api.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algafoods.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafoods.api.assembler.CozinhaModelDisassembler;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.input.CozinhaInput;
import com.algaworks.algafoods.api.openapi.controller.CozinhaControlerOpenApi;
import com.algaworks.algafoods.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;
import com.algaworks.algafoods.domain.service.CadastroCozinhaService;

//@Controller
//@ResponseBody //Indica que a resposta dos m??todos devem ir para a resposta HTTP
@RestController //Substitui o @Controller e o @ResponseBody
//@RequestMapping (value="cozinhas", produces = MediaType.APPLICATION_JSON_VALUE) -- Aplica na classe inteira
@RequestMapping(path="/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControlerOpenApi{
	
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaModelDisassembler cozinhaModelDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	
	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)//Significa que esse m??todo produz algo
	public PagedModel<CozinhaModel> listar(@PageableDefault (size=4) Pageable pageable){//Para paginar a consulta
				
		//PageableDefault define o tamanho da pagina????o
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);//Para paginar a consulta
		//QUando colocamos o Page no retorno l?? em cima (public Page<cozinhaModel>) teremos um quantitativo de 
		//p??ginas dispon??veis na pagina????o
		
		/* 
		 * L?? no postman. Coloca size para determinar o tamanho da pagina??ao (quantos resultados por p??gina?)
		 *  e page para determinar qual p??gina iremos (come??ando na zero)
		 * querer trazer no resultado
		 * Para ordenar basta colocar sort e no value coloca o campo que quer ordenar (nome, desc, etc) 
		 */
		
		/*
		 * O que estpa implementado embaixo era utilizado sem  Hateoas. Depois que passamos a 
		 * utilizar o Hateoas chamamos um conversor de Page para PageModel e a implementa????o
		 * fica mais simples. Basta fazer uma inje????o de PagedResourcesAssembler
		 * 
		 * --------------- ATEN????O ---------------
		 * Ao fazer isso eu n??o preciso mais do PageJsonSerializer que est?? no core/jackson
		 * pois este pagedResourceAssembler j?? faz tudo que este Serializador faz
		 */
		
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);//eu passo um page normal e um conversor de model que ?? o assembler implementado com modelMapper
				
//		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler
//				.toCollectModel(cozinhasPage.getContent());//Para paginar a resposta
//		 Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, 
//				cozinhasPage.getTotalElements());//Para pegar o total de p??ginas
		 return cozinhasPagedModel;
	}
	
	//@ResponseStatus(HttpStatus.CREATED)//Define o c??digo de status de retorno - Forma mais manual=,,,,,,
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable("cozinhaId")Long id) {
		return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar (@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaModelDisassembler.toDomainObject(cozinhaInput);
		
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));	
	}
	
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar (@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInput cozinhaInput){
			Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
			
			cozinhaModelDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
			
//			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
		
	//2?? Forma de fazer
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	

}
