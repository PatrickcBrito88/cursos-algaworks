package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafoods.api.assembler.GrupoModelAssembler;
import com.algaworks.algafoods.api.assembler.GrupoModelDisassembler;
import com.algaworks.algafoods.api.model.GrupoModel;
import com.algaworks.algafoods.api.model.input.GrupoInput;
import com.algaworks.algafoods.api.openapi.controller.GrupoControlerOpenApi;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.repository.GrupoRepository;
import com.algaworks.algafoods.domain.service.CadastroGrupoService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping(path="/grupos", produces = MediaType.APPLICATION_JSON_VALUE) // Com o produces o Swagger escaneia e seta na documentação
public class GrupoController implements GrupoControlerOpenApi {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoModelDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar() {
//		List<Grupo> todosGrupos = grupoRepository.findAll();
		
//		return grupoModelAssembler.toCollectionModel(todosGrupos);
		return null;
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toObjectModel(grupoInput);
		
		grupo = cadastroGrupo.salvar(grupo);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToObject(grupoInput, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoModelAssembler.toModel(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);	
	}
	
}
