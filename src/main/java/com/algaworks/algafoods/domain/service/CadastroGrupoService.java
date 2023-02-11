package com.algaworks.algafoods.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.api.assembler.GrupoModelAssembler;
import com.algaworks.algafoods.api.assembler.GrupoModelDisassembler;
import com.algaworks.algafoods.api.model.GrupoModel;
import com.algaworks.algafoods.api.model.input.GrupoInput;
import com.algaworks.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.model.Permissao;
import com.algaworks.algafoods.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissao;
	
	private static final String MSG_GRUPO_EM_USO 
	= "Grupo de código %d não pode ser removido, pois está em uso";
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
			.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public void adicionarPermissao (Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao persmissao = cadastroPermissao.buscarOuFalhar(permissaoId);	
		grupo.adicionarPermissao(persmissao);
	}
	
	@Transactional
	public void removerPermissao (Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao persmissao = cadastroPermissao.buscarOuFalhar(permissaoId);	
		grupo.removerPermissao(persmissao);
	}
}
