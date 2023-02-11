					package com.algaworks.algafoods.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafoods.api.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafoods.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafoods.api.assembler.RestauranteModelDisassembler;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafoods.api.model.RestauranteBasicoModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.api.model.view.RestauranteView;
import com.algaworks.algafoods.api.openapi.controller.RestauranteControlerOpenApi;
import com.algaworks.algafoods.api.openapi.model.RestauranteBasicoOpenApi;
import com.algaworks.algafoods.core.validation.Groups;
import com.algaworks.algafoods.core.validation.ValidacaoException;
import com.algaworks.algafoods.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.RestauranteRepository;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin(maxAge=20)//(maxAge=10)//(origins = "http://www.algafood.local:8000")//(origins = "http://www.algafood.local:8000")
//Podemos habilitar o Cors de forma global para não precisar ficar habilitando método por método ou classe por classe
@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControlerOpenApi{
	
	/*
	 * Preflight é uma chamada de verificação.
	 * Os browsers enviam uma requisição para o servidor para saber quais tipos de requisição os servidores aceitam.
	 * "Estou prestes a te mandar uma requisição. Você me permite ?"
	 * O navegador faz um Options para verificar
	 */

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteModelDisassembler restauranteModelDisassemler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired // A partir de uma instancia que será injetada em validator podemos fazer a
				// validação
	private SmartValidator validator;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel Cadastro(@RequestBody @Valid RestauranteInput restauranteInput) {
		// @Validated(Groups.CadastroRestaurante.class)
		// Essa constraint está agrupada no grupo Cadastro Restaurante
		// @Valid valida com grupo default
		// @Validated (valida pelo grupo que está marcado
		try {
			Restaurante restaurante = restauranteModelDisassemler.toDomainObject(restauranteInput);// 1ª Coversão
																									// Representação de
																									// entrada para
																									// entidade
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));// 2ª conversão Entidade
																								// para representaçaõ de
																								// saída
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel Atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			restauranteModelDisassemler.copyToDomainObject(restauranteInput, restauranteAtual);
//			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
//					"endereco", "dataCadastro", "produtos");
//			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));

		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	// Usando JsonViewer

//	@GetMapping
//	public MappingJacksonValue listarResumido(@RequestParam(required = false) String projecao) {
//		// MappingJacksonValue = Envelopar. Atribuindo uma JsonViewer dinamicamente
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectModel(restaurantes);
//
//		MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restaurantesModel);
//
//		restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equals(projecao)) {
//			restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else {
//			if ("completo".equals(projecao)) {
//				restauranteWrapper.setSerializationView(null);
//			}
//		}
//
//		return restauranteWrapper;
//
//	}

	
	 //Modelos de filtro
	@GetMapping()
	//@JsonView(RestauranteView.Resumo.class)//Json Viewer está dando problema com o CollectionModel
	public CollectionModel<RestauranteBasicoModel> listar() {
//		return ResponseEntity.ok()
				//.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*") //Essa é a ação manual, mas também pode fazer pelo Spring adiconando a anotação @CrossOrigin. Pode ser na função ou no controlador inteiro
				//Para o navegador permitir o chamamento de URLS cruzadas (http://www.algafood.local:8000), por exemplo (api.algafood.local) (aula 16.3)
				//Ou então coloca o * que "libera geral"
//				.body(restauranteModelAssembler.toCollectModel(restauranteRepository.findAll()));
		 return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
				
	}
	
	
	@ApiOperation(value = "Lista Restaurantes", hidden=true)//Hidden=true, significa que essa operação fica oculta pois vai aparecer a de cima
	@GetMapping(params = "projecao=apenas-nome")
	//@JsonView(RestauranteView.ApenasNome.class)// Marco qual filtro será utilizado de acordo com o que foi marcado na Model
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
		return restauranteApenasNomeModelAssembler
					.toCollectionModel(restauranteRepository.findAll());	
	}

//	
//	@GetMapping(params = "projecao=resumo")
//	@JsonView(RestauranteView.Resumo.class)// Marco qual filtro será utilizado de acordo com o que foi marcado na Model
//	public List<RestauranteModel> listarResumido() {
//		return listar();
//	}
//	------------------------

	@GetMapping("/{RestauranteID}")
	public RestauranteModel buscar(@PathVariable("RestauranteID") Long id) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		RestauranteModel restauranteModel = restauranteModelAssembler.toModel(restaurante);

		return restauranteModel;
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativacoes(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativacoes(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

//	@PatchMapping("/{restauranteId}")
//	public RestauranteModel atualizarParcial(@PathVariable("restauranteId") Long id,
//			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		// o Patch atualiza parcialmente
//		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
//		
//		merge(campos, restauranteAtual, request);
//		
//		//Validar o novo restaurante parcialmente. 
//		validate(restauranteAtual,"restaurante");
//		
//		return Atualiza(id, restauranteAtual);
//
//}
//	
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, 
//				objectName);
//		
//		validator.validate(restaurante, bindingResult);
//		
//		//Ele verifica se tem erros dentro do objeto. Se tiver ele traz
//		if (bindingResult.hasErrors()) {//Tem erro aí dentro ???
//			throw new ValidacaoException(bindingResult); //Aula 9.19
//		}
//		
//	}
//
//	public void merge (Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//		//configuração para lançar excessão quando tentar atualizar parcialmente
//		//algo que está sendo ignorado
//		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//		//lança excessão quando tenta jogar um atributo que nem existe na entidade
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//		
//		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//		
//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
////			System.out.println(nomePropriedade + "=" + valorPropriedade + " = " + novoValor);
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//		
//		});
//		
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//		 throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}
}
