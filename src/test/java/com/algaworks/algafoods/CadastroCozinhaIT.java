package com.algaworks.algafoods;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;
import com.algaworks.algafoods.util.DatabaseCleaner;
import com.algaworks.algafoods.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//para o servidor de teste levantar uma aplicação com uma porta aleatória
@TestPropertySource("/application-test.properties")//Indica que irá o application properties do source de teste
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port; 
	//Como está usando o Random_port (porta aleatória),
	//criamos essa variável para pegar a porta aleatória que foi criada
	//essa variável vai ser injetada lá no port
	
	@Autowired
	private Flyway flyWay;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private String cozinhaChinesa;
	
	
	private Cozinha cozinhaAmericana;
	
	private int quantidadeCozinhasCadastradas;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	
	/*
	 * Adicionado ao projeto o plugin do maven fail-safe
	 * Ele permite que a build do projeto suba sem realizar os testes de integração,
	 * tendo em vista que são muito caros processualmente.
	 * Por padrão as classes de Testes de Integração devem terminar com o sufixo IT
	 */
	
	//Teste de Integração Comentado pq o curso optou por não ter
//	@Autowired
//	private CadastroCozinhaService cadastroCozinhaService;
//	
//	@Test
//	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
//		//Cenário
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//		
//		// Ação
//		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
//		
//		
//		//Validação
//		//Assegurar que nova cozinha (e Id da Cozinha) is not null
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//	
//	@Test
//	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//		// Cenário
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//		
//		//Ação junto com validação
//		ConstraintViolationException erroEsperado = 
//				Assertions.assertThrows(ConstraintViolationException.class, () ->{
//					cadastroCozinhaService.salvar(novaCozinha);
//				});
//				
//		//Validação
//		assertThat(erroEsperado).isNotNull();
//		
//	}
//	
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		//Cenario
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setId(1L);
//		
//		//Ação
//		EntidadeEmUsoException erroEsperado = 
//				Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
//					cadastroCozinhaService.excluir(1L);
//				});
//		
//		//Validação
//		assertThat(erroEsperado).isNotNull();
//	}
//	
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
//		//Cenario
//				
//				
//				//Ação
//				CozinhaNaoEncontradaException erroEsperado = 
//						Assertions.assertThrows(CozinhaNaoEncontradaException.class, () ->{
//							cadastroCozinhaService.excluir(100L);
//						});
//				
//				//Validação
//				assertThat(erroEsperado).isNotNull();
//	}
	
	//Setup de testes
	@BeforeEach//Anotação que faz com que o método seja executado sempre antes de cada teste
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();//Habilitando o Log da requisição e da resposta
		RestAssured.basePath=("/cozinhas");//caminho
		RestAssured.port=(port);//na porta (note que está pegando a porta aleatória e injetando aqui)
		
		//comando para ler um arquivo Json através da classe ResourceUtils dentro de Util
		//Depois passa direto no método
		cozinhaChinesa = ResourceUtils.getContentFromResource(
				"/JsonData/cozinhaChinesa.json");
		
		dataBaseCleaner.clearTables();//Limpa a base de dados
		prepararDados();
		
		//flyWay.migrate();//Reseta a base de dados com base no afterMigrate
	}
	
	private void prepararDados() {//Insere uma massa de dados necessária para rodar os testes
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaAmericana=cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas=(int)cozinhaRepository.count();//Retorna o quantitativo de cozinhas cadastradas
		
	}
	
	
	//Teste de API
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		//Existem várias bibliotecas. No curso será usada RestAssured
		//Tem que adicionar pelo pom
		given()//dado que  --RestAssured feito com importação estática. Preferences, Java, Editor, Content Assists, Favorites
			.accept(ContentType.JSON)//que aceita JSON
		.when()//quando
			.get()//eu fizer um get
		.then()//então
			.statusCode(200);//o status tem que ser 200 ou httpStatus.Ok.value()
	}
	
	@Test
	public void testeConterNumeroDeCozinhasCadastradas_QuandoConsutarCozinhas() {
		//Existem várias bibliotecas. No curso será usada RestAssured
		//Tem que adicionar pelo pom
		given()//dado que  --RestAssured feito com importação estática. Preferences, Java, Editor, Content Assists, Favorites
			.accept(ContentType.JSON)//que aceita JSON
		.when()//quando
			.get()//eu fizer um get
		.then()//então
			.body("nome", Matchers.hasSize(quantidadeCozinhasCadastradas));//Matchers biblioteca com regras de correspondencias
			//Esta validando que tem 2 nomes na resposta
			//.body("nome", Matchers.hasItems("Tailandesa","Indiana"));//Verifica se no resultado possui esses 2 resultados
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()//dado que 
			.body(cozinhaChinesa)//eu tenho um corpo
			.contentType(ContentType.JSON)//em json
			.accept(ContentType.JSON)//e aceito uma resposta em json
		.when()
			.post()//quando eu fizer um post
		.then()
			.statusCode(HttpStatus.CREATED.value());//então eu aceito Status Created
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
	given()//dado que 
		.pathParam("cozinhaId", cozinhaAmericana.getId())//possuo uma variavel cozinhaId, com valor 2
		.accept(ContentType.JSON)//e aceito uma resposta em json
	.when()
		.get("/{cozinhaId}")//faço um get passando a variavel por parametro
	.then()
		.statusCode(HttpStatus.OK.value())//então eu aceito Status OK
		.body("nome", equalTo("Americana"));//e um corpo na resposta de valor Tailandesa
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaInexistente() {
	given()//dado que 
		.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)//possuo uma variavel cozinhaId, com valor 2
		.accept(ContentType.JSON)//e aceito uma resposta em json
	.when()
		.get("/{cozinhaId}")//faço um get passando a variavel por parametro
	.then()
		.statusCode(HttpStatus.NOT_FOUND.value());//então eu aceito Status OK
		
	}
	

}
