package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.CadastroPermissaoNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.PermissaoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.model.Permissao;
import com.algaworks.algafoods.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public final static String MSG_ENTIDADE_EM_USO =
			"Permissão de código %d não pode ser excluída pois está em uso.";
	
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	public List<Permissao> listar(){
		List<Permissao> lista = permissaoRepository.findAll();
		return lista;
	}
	
	public Permissao buscar (Long id) {
		return buscarOuFalhar(id);
	}
	
	public void remover (Long id) {
		buscarOuFalhar(id);
		try {
		permissaoRepository.deleteById(id);
		permissaoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, id));
		}
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
			.orElseThrow(() -> new PermissaoNaoEncontradoException(permissaoId));
	}
	
}
