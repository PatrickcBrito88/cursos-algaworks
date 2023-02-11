package com.algaworks.algafoods.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafoods.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafoods.api.model.FotoProdutoModel;
import com.algaworks.algafoods.api.model.input.FotoProdutoInput;
import com.algaworks.algafoods.api.openapi.controller.ProdutoControlerOpenApi;
import com.algaworks.algafoods.api.openapi.controller.ProdutoFotoControlerOpenApi;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.FotoProduto;
import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.service.CadastroProdutoService;
import com.algaworks.algafoods.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafoods.domain.service.FotoStorageService;
import com.algaworks.algafoods.domain.service.FotoStorageService.FotoRecuperada;
import com.google.common.net.HttpHeaders;

@RestController
@RequestMapping(path="/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces=MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController  implements ProdutoFotoControlerOpenApi{

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoStorageService fotoStorageService;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput,
			@RequestPart (required=true) MultipartFile arquivo) throws IOException {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		//@RequestPart (required=true) MultipartFile arquivo - Este argumento é uma maneira de passar o content type pela documentação
		//Aula 18.36
//		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping
	public FotoProdutoModel buscar (@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto=catalogoFotoProduto.buscarOuFalhar(produtoId, restauranteId);
		return fotoProdutoModelAssembler
				.toModel(fotoProduto);
	}
	
	//Metodo que traz a foto
	@GetMapping(produces=MediaType.ALL_VALUE)//A diferença na assinatura é o produces. A URI é a mesma
	public ResponseEntity<?> servirFoto (@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestHeader (name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		//@RequesHeader - Pega o valor que o consumidor coloca no Header
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(produtoId, restauranteId);
			
			//Validar o MediaType
			//Verificar o mediaType da foto armazenada
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			
			//Pega os headers passados pelo consumidor
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatbilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temInputStream()) {
			/*
			 * Este método retorna tanto para armazenamento local quanto para nuvem.
			 * Se for local ele retorna um InputStream.
			 * Se for nuvem retorna uma URL pelo Headers
			 */				
				//Armazenamento Local

				
				return ResponseEntity.ok()
					.contentType(mediaTypeFoto)//Retorna no cabeçalho o content type exato do arquivo que está armazenado
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			} else {
				//Nuvem
				
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
						
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();//Se deixar sem esse try catch dá erro 406 ou 500
			//pois esse MediaType não aceita Json (do buscar ou falhar)
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarFoto (@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		catalogoFotoProduto.deletar(produtoId, restauranteId);
	}
	
	private void verificarCompatbilidadeMediaType (MediaType mediaTypeFoto,
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));//Pelo menos um verdadeiro
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
		
		/*
		 * Faz um stream na lista de mediaTypesAceitas, dali faz um anymatch(ou seja, se achar 1)
		 * E chama uma função para cada item da lista e verifica se é compatível (compatiblewith) com a mediaType
		 * da foto.
		 */
		
	}

	
	
		

}




// Exercício para cadastrar Foto de maneira Local

//	@PutMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
//	public void atualizarFoto(@PathVariable Long restauranteId,
//			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
//		//Neste caso se usar o RequestBody dá erro
//		
//		var nomeArquivo = UUID.randomUUID().toString()
//				+"_" + fotoProdutoInput.getArquivo().getOriginalFilename(); // Renomeia o arquivo
//		
//		var arquivoFoto = Path.of("/Users/patri/OneDrive/Área de Trabalho/catalogo", nomeArquivo); // Para onde vai mandar
//		
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivoFoto);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		
//		
//		try {
//			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
