package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.algaworks.algafoods.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafoods.api.assembler.PermissaoModelDisassembler;
import com.algaworks.algafoods.api.model.PermissaoModel;
import com.algaworks.algafoods.api.model.input.PermissaoInput;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.PermissaoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.model.Permissao;
import com.algaworks.algafoods.domain.repository.PermissaoRepository;
import com.algaworks.algafoods.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

	@Autowired
	private CadastroPermissaoService cadastroPermissao;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private PermissaoModelDisassembler permissaoModelDisassembler;
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PermissaoModel> listar() {
		return permissaoModelAssembler.toCollectModel(cadastroPermissao.listar());
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
			.orElseThrow(() -> new PermissaoNaoEncontradoException(permissaoId));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PermissaoModel buscar(@PathVariable("id") Long id) {
		return permissaoModelAssembler
				.toModel(buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoModel salvar(@RequestBody @Valid PermissaoInput permissaoInput) {
		Permissao permissao = permissaoModelDisassembler.toObjectModel(permissaoInput);
		return permissaoModelAssembler
				.toModel(permissaoRepository.save(permissao));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PermissaoModel atualizar(@RequestBody PermissaoInput permissaoInput, @PathVariable("id") Long id) {
		Permissao permissao = buscarOuFalhar(id);
		permissaoModelDisassembler.copyToObject(permissaoInput, permissao);
		return permissaoModelAssembler
				.toModel(permissaoRepository.save(permissao));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable ("id") Long id){
		cadastroPermissao.remover(id);
	}
		
}
