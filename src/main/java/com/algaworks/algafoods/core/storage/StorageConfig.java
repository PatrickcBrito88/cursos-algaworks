package com.algaworks.algafoods.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoods.core.storage.StorageProperties.TipoStorage;
import com.algaworks.algafoods.domain.service.FotoStorageService;
import com.algaworks.algafoods.infrastrucutre.service.storage.LocalFotoStorageService;
import com.algaworks.algafoods.infrastrucutre.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {
	
	//Configuração para utilização da API da Amazon
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean //Produz instância de amazonS3
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()//Criando Builder
				.withCredentials(new AWSStaticCredentialsProvider(credentials))//Passando credenciais
				.withRegion(storageProperties.getS3().getRegiao())//Passando a região
				.build();//constrói
	}
	
	@Bean//Método que retorna instância de FotoStorageService
	//Aqui faz uma condiciional para retornar 1 tipo ou outro da interface. Ou de S3 ou de Local. Assim pode
	//deixar as duas classes de services ativadas com Service
	public FotoStorageService fotoStorageService() {
		if (storageProperties.getTipo().equals(TipoStorage.S3)) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}

}
