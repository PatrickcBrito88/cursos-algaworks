package com.algaworks.algafoods.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Estado;
import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto salvar (Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto buscarOuFalhar (Long produtoId, Long restauranteId) {
		Produto produto = produtoRepository.findById(restauranteId, produtoId).
				orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
		return produto;	
	}
	
}
