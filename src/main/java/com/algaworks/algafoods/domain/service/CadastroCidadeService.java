package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.repository.CidadeRepository;
import com.algaworks.algafoods.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida,"
			+ " pois está em uso";
	
	

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	
	public Cidade buscarOuFalhar (Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
	@Transactional
	public Cidade salvar (Cidade cidade) {
		
		Estado estado = cadastroEstado.buscarOuFalhar(cidade.getEstado().getId());
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);	
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {	
				throw new CidadeNaoEncontradaException(id);
				
		} catch (DataIntegrityViolationException e) {		
				throw new EntidadeEmUsoException (
					String.format(MSG_CIDADE_EM_USO,id));
				}
		}
	
	}
	

