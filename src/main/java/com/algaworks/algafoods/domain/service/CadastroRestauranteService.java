package com.algaworks.algafoods.domain.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.*;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;
import com.algaworks.algafoods.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		Cidade cidade = cadastroCidade.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
		
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
		
		/*
		 * Anotou como @transactional pois poderiam ter transações antes que seriam executadas
		 * (como o save do Repository e outras não.
		 * Então anotando o método salvar como transactional eu permite que ocorra o rollback
		 * se alguma linha do método falhar
		 * 
		 */
		
	}
	
	public Restaurante buscarOuFalhar (Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void ativar (Long restauranteId) {
	
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
		
		/*
		 *Quando estou dentro de um contexto gerenciado pelo JPA, não preciso fazer o restauranteRepositoru.save
		 *Se eu altero o estado de um atributo de uma entidade gerenciada pelo JPA automaticamente ele sincroniza no
		 *banco de dados
		 */		
		
	}
	
	@Transactional
	public void inativar (Long restauranteId) {
		
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();
		
		/*
		 *Quando estou dentro de um contexto gerenciado pelo JPA, não preciso fazer o restauranteRepositoru.save
		 *Se eu altero o estado de um atributo de uma entidade gerenciada pelo JPA automaticamente ele sincroniza no
		 *banco de dados
		 */		
	}
	
	@Transactional
	public void ativar (List<Long>restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar (List<Long>restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void abrir (Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.abrir();
	}
	
	@Transactional
	public void fechar (Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.fechar();
	}
	
	@Transactional
	public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.removeFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void adicionaFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionaFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void adicionaResponsavel (Long restauranteId, Long usuarioid) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioid);
		restaurante.adicionaResponsavel(usuario);
	}
	
	@Transactional
	public void removeResponsavel (Long restauranteId, Long usuarioid) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioid);
		restaurante.removeResponsavel(usuario);
	}

		
}
