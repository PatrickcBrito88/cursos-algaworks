package com.algaworks.algafoods.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafoods.core.validation.ValidacaoException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice //Faz com que capture excess??es em todo o projeto
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	private static final String MSG_ERRO_USUARIO_FINAL
		= "Ocorreu um erro interno inesperado do sistema. Tente novamente e se o "
				+ "problema persistir, entre em contato com o administrador do sistema";
	
	private static final String MSG_DADOS_INVALIDOS
		="Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.";
	
	
	private Problem.ProblemBuilder createProblemBuilder (HttpStatus status,
			ProblemType problemType, String detail){
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now());
	}
	
	//Concatenar o caminho do erro com . (ponto)
	private String joinPath(List<Reference> references) {
		return references.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
	
	/*Como essa chamada de HttpMediaTypeNotAcceptable n??o pode ser representada pelo ProblemType (uma vez que 
	 * o accept no postman ?? uma image, ent??o temos que sobrescrever o m??todo original passando o responseStatus 
	 * correto
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	
//	protected ResponseEntity<Object> handleMediaTypeNotAcceptable(
//			HttpMediaTypeNotAcceptableException e, HttpHeaders headers, HttpStatus status, WebRequest request){
//		return ResponseEntity.status(status).headers(headers).build();	
//		
//	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(
			EntidadeEmUsoException e, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		
		return handleExceptionInternal(e, problem, new HttpHeaders(),
				status, request);		
		
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request){
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		status = HttpStatus.BAD_REQUEST;
		String detail = MSG_DADOS_INVALIDOS;
		
			
		//Criando uma lista de fields com problema
		//Estava usando Field Erro que pegava apenas erro de propriedade (getFieldErros()
		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());//Pega a mensagem do messageproperties
					
					String name=objectError.getObjectName();
					
					//Se for classe ?? objecterror, se for propriedade ?? fieldError
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_DADOS_INVALIDOS)
				.objects(problemObjects)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);	
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)//?? um m??todo que trata captura todas as excess??es da EntidadeNaoEncontrada
	public ResponseEntity<?> handleEstadoNaoEncontradoException(
			EntidadeNaoEncontradaException e, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;// Defifne o type e o title
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		
		return handleExceptionInternal(e, problem, new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)//?? um m??todo que trata captura todas as excess??es da EntidadeNaoEncontrada
	public ResponseEntity<?> handleNegocioException(
			NegocioException e, WebRequest request){
	
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		
		return handleExceptionInternal(e, problem, new HttpHeaders(),
				status, request);
	}
	
	//Ponto central onde customiza o corpo da resposta
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, 
			Object body, HttpHeaders headers,HttpStatus status, WebRequest request) {
		
		if (body == null) {
		body = Problem.builder()
				.title(status.getReasonPhrase())//Pequena descri????o que descreve o status que est?? vindo
				.status(status.value())
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.timestamp(OffsetDateTime.now())
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String)body)
					.status(status.value())
					.userMessage(MSG_ERRO_USUARIO_FINAL)
					.timestamp(OffsetDateTime.now())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	};
	
	//erro para tratar a digita??a?? de algo inv??lido no corpo da requisi????o
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Tem que acrescentar a depend??ncia commons-lang3 no pom.xml
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, 
					headers, status, request);
		}
		
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause,
					headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisi????o est?? inv??lido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Pego o caminho do problema
		String path=joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail=String.format("A requisi????o solicitou a propriedade %s. Esta propriedade"
				+ " est?? ignorada ou n??o existe", path);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}


	//Metodo para especificar onde est?? o erro do InvalidFormat
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//M??todo que pega o caminho completo do atributo, separando pelo . (ponto)
		String path=joinPath(ex.getPath());
		
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail=String.format("A propriedade '%s' recebeu o valor '%s',"
				+ " que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o"
				+ " tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		String detail = String.format("O par??metro de URL '%s' recebeu o valor '%s', "
				+ "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = ex.getRequestURL();
		
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que voc?? tentou acessar, ?? inexistente.",
				path);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<?>handleUncaught (Exception e, WebRequest request) {
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = MSG_ERRO_USUARIO_FINAL;
		e.printStackTrace();
		log.error(e.getMessage(),e);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();	
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);	
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request){
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
	
	
}
