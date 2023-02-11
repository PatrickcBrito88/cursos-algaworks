package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafoods.api.assembler.EstadoModelAssembler;
import com.algaworks.algafoods.api.assembler.EstadoModelDisassembler;
import com.algaworks.algafoods.api.model.EstadoModel;
import com.algaworks.algafoods.api.model.input.EstadoInput;
import com.algaworks.algafoods.api.openapi.controller.EstadoControlerOpenApi;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.repository.EstadoRepository;
import com.algaworks.algafoods.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path="/estados", produces=MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControlerOpenApi{

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoModelDisassembler estadoModelDisassembler;

	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable("estadoId") Long estadoId) {
		return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}

	@PostMapping
	public EstadoModel salvar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoModelDisassembler.toObjectModel(estadoInput);
		return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		
		estadoModelDisassembler.copyToObject(estadoInput, estadoAtual);
		
//		BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable("estadoId") Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}
