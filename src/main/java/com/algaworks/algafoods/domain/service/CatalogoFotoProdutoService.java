package com.algaworks.algafoods.domain.service;



import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafoods.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafoods.domain.model.FotoProduto;
import com.algaworks.algafoods.domain.repository.ProdutoRepository;
import com.algaworks.algafoods.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	/*
	 * Produto Repositoy não possui o método salvar passando uma foto como parâmetro.
	 * Então criou-se uma interface ProdutoRepositoryQueries com um método que salva
	 * e o ProdutoRepository estendeu esta interface
	 */
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	public FotoProduto buscarOuFalhar (Long produtoId, Long restauranteId) {
		FotoProduto fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId).
				orElseThrow(() -> new FotoProdutoNaoEncontradoException(produtoId, restauranteId));
		return fotoProduto;	
	}
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente=null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {
			nomeArquivoExistente=fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		//O save subiu pq se der algum problema no save, ele não salva a foto antes. 
		//O que acontecer de errado no banco de dados, ele dá rollback, mas isso não acontece com o armazenamento
		//Por isso o armazenamento ficou para baixo do save.
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void deletar (Long produtoId, Long restauranteId) {
		FotoProduto fotoProduto = buscarOuFalhar(produtoId, restauranteId);
		produtoRepository.delete(fotoProduto);// O produtoRepository com esta assinatura delete (foto em parametro) chama o produtoQuery
		fotoStorageService.remover(fotoProduto.getNomeArquivo());
		produtoRepository.flush();
		
	}
	
	
	
}
