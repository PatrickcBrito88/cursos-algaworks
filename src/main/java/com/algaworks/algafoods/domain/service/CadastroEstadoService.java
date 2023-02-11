package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	private static final String MSG_ESTADO_EM_USO 
	= "Estado de código %d não pode ser removido, pois está em uso";
	
	
	public Estado buscarOuFalhar (Long estadoId) {
		Estado estado = estadoRepository.findById(estadoId).
				orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
		return estado;
				
	}

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long id) {
		try {
		estadoRepository.deleteById(id);
		estadoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(id);
		}
	}

		
}
