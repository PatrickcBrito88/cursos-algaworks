package com.algaworks.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.GrupoModelAssembler;
import com.algaworks.algafoods.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafoods.api.model.PermissaoModel;
import com.algaworks.algafoods.api.openapi.controller.GrupoPermissaoControlerOpenApi;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path="/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControlerOpenApi {
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired 
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PermissaoModel> listarPermissoes (@PathVariable Long grupoId){
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		return permissaoModelAssembler
				.toCollectModel(grupo.getPermissoes());
		
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarPermissao (@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.adicionarPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPermissao (@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.removerPermissao(grupoId, permissaoId);
	}

}
