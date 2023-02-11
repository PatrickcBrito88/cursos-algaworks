package com.algaworks.algafoods;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafoods.domain.repository.CustomJpaRepository;
import com.algaworks.algafoods.infrastrucutre.repository.CustomJpaRepositoryImpl;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodsApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));//Configuração para devolver ao consumidor o horário em UTC, assim como está cadastrado no banco
		SpringApplication.run(AlgafoodsApplication.class, args);
	}

}
