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
import com.algaworks.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO 
	= "Cozinha de código %d não pode ser removida, pois está em uso";
	//Essa classe colocamos as regras de negócio
	//Classe de serviço não retorna ResponseEntity
	
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha salvar (Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void excluir (Long cozinhaId) {
		buscarOuFalhar(cozinhaId);
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();//Força a execução pelo JPA por causa do @transactional que colocamos lá em cima
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO,cozinhaId));
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}
		
	}
	
	public Cozinha buscarOuFalhar (Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
//	@Transactional
//	public Cozinha buscar (Long id) {
//		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//		
//		if (cozinha.isEmpty()) {
//			throw new CozinhaNaoEncontradaException(id);
//		} 
//		return cozinha.get();// tem que usar o get por causa do Optional		
//	}
//	
//	
//	
//	
//	
//	
//	
//	public Cozinha atualizar2 (Cozinha cozinha, Long cozinhaId) {
//		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
//		
//		if (cozinhaAtual.isEmpty()) {
//			throw new CidadeNaoEncontradaException(cozinhaId);
//			
//		}
//		BeanUtils.copyProperties(cozinha, cozinhaAtual.get(),"id");
//		Cozinha cozinhaSalva = cozinhaRepository.save(cozinhaAtual.get());
//		return cozinhaSalva;
//				
//	}
//	
//	public Cozinha atualizar (Cozinha cozinha, Long cozinhaId) {
//		Cozinha cozinhaAtual = buscarOuFalhar(cozinhaId);
//		BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");
//		return salvar(cozinhaAtual);
//	}
//	
//	public List<Cozinha> listar() {
//		return cozinhaRepository.findAll();
//	}
}
