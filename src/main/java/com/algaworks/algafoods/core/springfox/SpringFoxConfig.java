package com.algaworks.algafoods.core.springfox;


import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafoods.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafoods.api.openapi.model.PedidosResumoModelOpenApi;
import com.amazonaws.auth.policy.Resource;
import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import({ springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})//Importar classe de valida????o autom??tica do swagger. Acho que n??o precisa da vers??o 3.00 em diante
public class SpringFoxConfig implements WebMvcConfigurer{
	
	
	@Bean
	public Docket apiDocket() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)//Significa sum??rio
				.select()//Builder para selecionar os endpoints que ser??o expostos
					//.apis(RequestHandlerSelectors.any())//Ou seja, traz tudo que encontrar
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafoods.api"))//Quero que escaneie os controladores desse pacote
					//tamb??m posso fazer
	//				.apis(Predicates.and(
	//						RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"),
	//						RequestHandlerSelectors.basePackage("com.algaworks.algafood.outro")))
					//.paths(PathSelectors.ant("/restaurantes/*"))//Assim fazemos um filtro do caminho. Neste casso aparece apenas restaurantes
					.paths(PathSelectors.any())//Vem todos os caminhos na documenta????o
					.build()
				.useDefaultResponseMessages(false)//Desabilita os c??digos de erros. Mas ?? melhor usar o global, conforme acima
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				//Par??metro impl??cito de maneira global
//				.globalOperationParameters(Arrays.asList( //Explica????o l?? em baixo - Par??metro impl??cito
//						new ParameterBuilder()
//						.name("campos")
//						.description("Nomes das propriedades para filtrar na resposta, separado por v??rgulas.")
//						.parameterType("query")
//						.modelRef(new ModelRef("String"))
//						.build()))
				.ignoredParameterTypes(ServletWebRequest.class,//Explica????o abaixo
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.additionalModels(typeResolver.resolve(Problem.class))//Adiciona um novo modelo que n??o foi mapeado pelo Swagger
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)//Aqui vem a substitui????o do tipo de pageable que retorna na documenta????o
				.alternateTypeRules(AlternateTypeRules.newRule
						(typeResolver.resolve(Page.class, CozinhaModel.class), CozinhasModelOpenApi.class))//Substitui????o de m??todos. Explica????o na classe CozinhasModelOpenApi
				.alternateTypeRules(AlternateTypeRules.newRule
						(typeResolver.resolve(Page.class, PedidoResumoModel.class), PedidosResumoModelOpenApi.class))//Substitui????o de m??todos. Explica????o na classe CozinhasModelOpenApi
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as Formas e Pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os Restaurantes"),
						new Tag("Estados", "Gerencia os Estados"),
						new Tag("Fluxo de Pedidos", "Gerencia o Fluxo de Pedidos"),
						new Tag("Produtos", "Gerencia os produtos"),
						new Tag("Usu??rios", "Gerencia os usu??rios"),
						new Tag("Estat??stica", "Gerencia as estat??sticas")
						);
				
		
		/*O Swagger adiciona uma depend??ncia transitiva chamada guava, vers??o 20.
		*O Squiddy tamb??m adiciona essa depend??ncia, mas na vers??o 19.
		*Sendo assim, exclu??mos pelo POM a depend??ncia do Squiddy atrav??s da tag <exclusion>
		*
		*www.algafood.local:8080/v2/api-docs - No Postman vem a documenta????o em Json
		*
		*WebMvcConfigurer - para funcionar o UI
		*
		*Para funcionar na web http://api.algafood.local:8080/swagger-ui/ *****ATEN????O - TEM QUE TER A BARRA
		*
		*@ApiOperation("Salvar uma cidade") - Em cima de cada m??todo para colocar na documenta????o o que cada m??todo faz
		*
		*@ApiParam(value = "ID de uma cidade", example = "1") dentro do par??metro, personaliza o nome do par??metro na documenta????o
		*
		*@ApiModel (value="cidade", description = "Representa uma cidade")// Para come??ar a personalizar o model no OpenApi Swagger
		*@ApiModelProperty(value="Nome da cidade", example = "Uberl??ndia") - Descrever o atributo dos models ou
		*@ApiModelProperty(example = "Minas Gerais", required=true) - Required true define o atributo como obrigat??rio no input
		*
		*@ApiResponses (@ApiResponse) Na classe cliente tem o modelo. Primeiro mapeamos os tipos de erros, conforme abaixo
		*depois mapeamos com ApiResponses na classe.
		*
		*Para a classe n??o ficar bagun??ada cria-se uma interface com a mesma assinatura da classe principal e a classe principal
		*implementa a interface. Na interface colocamos todas as anota????es de documenta????es
		*
		*Com o acr??scimo de produces = Media..... o Swagger vai escanear e vai setar na documenta????o
		*@RequestMapping(path="/grupos", produces = MediaType.APPLICATION_JSON_VALUE) // Com o produces o Swagger escaneia e seta na documenta????o
		*.directModelSubstitute(Pageable.class, PageableModelOpenApi.class) - Explica????o na p??gina PageableOpenApiModel
		*
		*ignoredParameterTypes(ServletWebRequest.class) faz com que ignore uma classe espec??fica.
		*No caso espec??fico esse servlet foi utilizado para buscar uma forma de pagamento e usou um servletwebrequest
		*Acontece que isso ?? injetado direto pelo Spring. O consumidor n??o precisa passar, ent??o temos 
		*que ignorar da documenta????o
		*
		*Par??metro impl??cito - O controler de pedidos tem o "campos" que serve como filtro. Ele n??o ?? mapeado automaticamente
		*pelo Swagger. Desta forma temos que fazer de maneira global ou pontual. O n??mero 1 foi de maneira global e serve 
		*para todos que possuem campos para pesquisa.
		*O par??metro campos ?? definido no Squiggly
		*
		*Para fazer de maneira pontual ?? muito mais f??cil, basta ir no m??todo onde tem o par??metro impl??cito e fazer o seguinte:
		*@ApiImplicitParams({
		*@ApiImplicitParam(value="Nomes das propriedades para filtrar na resposta, separados por v??rgula",
		*		name="campos", paramType="query", type="string")
		*	})
		*
		*/
	}
	
	//Representa????o do globalGetResponse que traz a defini????o dos erros que podem ocorrer
	
	
	//Personalizar a p??gina da documenta????o
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("Api Aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks.com.br"))
				.build();
		//Depois tem que passar no Docket				
	}
	
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}
