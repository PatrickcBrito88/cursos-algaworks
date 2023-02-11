package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafoods.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafoods.api.model.FormaPagamentoModel;
import com.algaworks.algafoods.api.model.input.FormaPagamentoInput;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.FormaPagamentoNaoEncontrada;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	public static final String MSG_FORMA_PAGAMENTO_EM_USO =
			"Forma de Pagamento de código %d não pode ser removida,pois está em uso.";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontrada(formaPagamentoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}

	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
			.orElseThrow(() -> new FormaPagamentoNaoEncontrada(formaPagamentoId));
	}

}
