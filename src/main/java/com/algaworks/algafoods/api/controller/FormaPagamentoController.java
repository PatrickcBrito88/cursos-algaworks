package com.algaworks.algafoods.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafoods.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafoods.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafoods.api.model.FormaPagamentoModel;
import com.algaworks.algafoods.api.model.input.FormaPagamentoInput;
import com.algaworks.algafoods.api.openapi.controller.FormasPagamentoControlerOpenApi;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoods.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path="/formapagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormasPagamentoControlerOpenApi{

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoAssembler formaPagamentoModelAssembler;

	@Autowired
	private FormaPagamentoDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar() {// ServletWebRequest request) {
		// Vamos validar se o e-Tag da requisição coincide ou não
		// Faremos uma consulta no banco de dados pedindo a última (max) data de
		// modificação das formas de pagamento

//						//*****Começando a implementar DEEP TAGs - Avaliar o esforço. Neste caso não vale*******
//		//Este comando desabilita o filter do E-tag, senão ele vai invalidar tudo que faremos abaixo
//		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
//		
//		//Coloca o eTag como 0
//		String eTag ="0";
//		
//		//Busca a última atualização
//		OffsetDateTime dataUltimaAtualizacao=formaPagamentoRepository.getDataUltimaAtualizacao();
//		
//		if (dataUltimaAtualizacao!=null) {//Se a lista estiver vazia
//			eTag=String.valueOf(dataUltimaAtualizacao.toEpochSecond());//Retorna o número de segundos desde 1970 até a data que está na atualização
//		}
//		
//		//Aqui ja temos condiçções de saber se continua ou não o processamento
//		if (request.checkNotModified(eTag)) {
//			// Ou seja não tem mofificação		
//			return null;
//		} else {
//		

		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

		CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler
				.toCollectionModel(todasFormasPagamentos);

		return ResponseEntity.ok()
				/*
				 * Módulo 17 CacheControl significa que esta requisição ficará no cache pelo
				 * tempo especificado no parâmetro. Após isso, se torna obsoleta. Só dá para
				 * testar utilizando um client javascript. O postman não aceita (ainda). Dá para
				 * usar utilizando um plugin do Google Chrome que simula o Postman em um
				 * ambiente Web Se chama Talend Api Tester
				 * 
				 * Existe uma ferramenta chamada Wireshark que monitora o tráfego de dados de
				 * uma rede local
				 * 
				 * 
				 * SHallow E-Tag O E-tag trabalha colocando uma tag em cada requisição. Quando
				 * uma requisição fica no cache ela fica com aquela. Se o tempo não expirar o
				 * client usa do cache. Se o tempo expirar ele compara a tag do cache com a tag
				 * do servidor Se for a mesma, continua usando a do cache Se for diferente o
				 * cache é atualizado Para ele funcionar basta criar o método que foi criado lá
				 * no core/web/webconfig
				 */
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				// .eTag(eTag) utilizado apenas para DeepTag (comentado lá em cima)
				.body(formasPagamentoModel);
		// } utilizado quando utilizamos Deep Tag
	}

//	@GetMapping("/{formaPagamentoId}")
//	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId) {
//		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
//
//		FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
//
//		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)).body(formaPagamentoModel);
//	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscarComDeepTag(@PathVariable Long formaPagamentoId,
			ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

		String eTag = "0";

		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}

		if (request.checkNotModified(eTag)) {
			return null;
		} else {

			FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

			FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

			return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
					.body(formaPagamentoModel);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toObjectModel(formaPagamentoInput);

		formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToObject(formaPagamentoInput, formaPagamentoAtual);

		formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

		return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);
	}

}
