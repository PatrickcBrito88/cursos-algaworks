	package com.algaworks.algafoods;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

import java.math.BigDecimal;

import javax.net.ssl.HttpsURLConnection;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;
import com.algaworks.algafoods.domain.repository.RestauranteRepository;
import com.algaworks.algafoods.util.DatabaseCleaner;
import com.algaworks.algafoods.util.ResourceUtils;

import io.restassured.RestAssured;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private String jsonRestauranteCorreto;
	
	private String jsonRestauranteSemCozinha;
	
	private String jsonRestauranteFreteNegativo;
	
	private String jsonRestauranteSemTaxaFrete;
	
	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE="Violação de Regra de Negócio";
	
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE="Dados inválidos";
	
	private static final int RESTAURANTE_ID_INEXISTENTE=100;
	
	@BeforeEach
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath=("/restaurante");
		RestAssured.port=port;
		
		jsonRestauranteCorreto=ResourceUtils.getContentFromResource(
				"/jsonData/Correto/restauranteNY.json");
		
		jsonRestauranteSemCozinha=ResourceUtils.getContentFromResource(
				"/jsonData/Incorreto/AraruamaRest_SemCozinha.json");
		
		jsonRestauranteFreteNegativo=ResourceUtils.getContentFromResource(
				"/jsonData/Incorreto/IguabaRest_FreteNegativo.json");
		
		jsonRestauranteSemTaxaFrete=ResourceUtils.getContentFromResource(
				"/jsonData/Incorreto/CaboFrioRest_SemTaxaFrete.json");
				
		
		dataBaseCleaner.clearTables();
		
		prepararDados();
		
	}
	
	private void prepararDados() {
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Cozinha1");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Cozinha1");
		cozinhaRepository.save(cozinha2);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Restaurante 1");
		restaurante1.setTaxaFrete(new BigDecimal(10));
		restaurante1.setCozinha(cozinha1);
		restauranteRepository.save(restaurante1);
		
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Restaurante 2");
		restaurante2.setTaxaFrete(new BigDecimal(10));
		restaurante2.setCozinha(cozinha2);
		restauranteRepository.save(restaurante2);
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		given()
			.accept(ContentType.JSON)
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.SC_CREATED);
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarSemTaxaFrete() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteSemTaxaFrete)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST)
			.body("title",equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarSemCozinha() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteSemCozinha)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
			//.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoRestauranteInexistente() {
		given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
		.when()
			.get("{restauranteId}")
		.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	

}
