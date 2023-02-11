package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.ResourceUriHelper;
import com.algaworks.algafoods.api.assembler.CidadeModelAssembler;
import com.algaworks.algafoods.api.assembler.CidadeModelDisassembler;
import com.algaworks.algafoods.api.model.CidadeModel;
import com.algaworks.algafoods.api.model.input.CidadeInput;
import com.algaworks.algafoods.api.openapi.controller.CidadeControlerOpenApi;
import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.repository.CidadeRepository;
import com.algaworks.algafoods.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path="/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControlerOpenApi {

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeModelDisassembler cidadeModelDisassembler;

	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		/*Tivemos que colocar uma CollectionModel no retorno porque List não tem 
		*representationModel, por isso mudamos para CollectionModel pq CollectionModel
		* tem RepresentationModel
		* 
		* Assim sendo, CollectionsModel também tem os métodos de inclusão de link
		*/
		
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable("cidadeId") Long id) {
		
		CidadeModel cidadeModel = cidadeModelAssembler
				.toModel(cadastroCidade.buscarOuFalhar(id));
		
//		cidadeModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class)//Builder adiciona domínio, protocolo e porta
//				.slash(cidadeModel.getId())// slash = /id
//				.withSelfRel());

		return cidadeModel;
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		try { 
			Cidade cidade = cidadeModelDisassembler.toObjectModel(cidadeInput);
			CidadeModel cidadeModel= cidadeModelAssembler
					.toModel(cadastroCidade.salvar(cidade));
			
			//HATEOAS
		ResourceUriHelper.addUriResponseHealter(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException (e.getMessage(), e);
		}
	}


	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable("cidadeId") Long id,	@RequestBody @Valid CidadeInput cidadeInput) {
		
			try {
				Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
				
				cidadeModelDisassembler.copyToObjectModel(cidadeInput, cidadeAtual);
//				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				
				return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
			} catch (EstadoNaoEncontradoException e) {
				throw new NegocioException(e.getMessage(), e);
			}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable("cidadeId") Long id) {
		cadastroCidade.remover(id);
	}
	
}
