package com.algaworks.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.GrupoModelAssembler;
import com.algaworks.algafoods.api.model.GrupoModel;
import com.algaworks.algafoods.api.openapi.controller.GruposUsuariosControlerOpenApi;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.service.CadastroGrupoService;
import com.algaworks.algafoods.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuariosGruposController implements GruposUsuariosControlerOpenApi{
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GrupoModel> listarGrupos (@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		return grupoModelAssembler.toCollectionModel(usuario.getGrupos());
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar (@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar (@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
	}

}
