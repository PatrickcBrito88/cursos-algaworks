package com.algaworks.algafoods.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.Region;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

	private Local local = new Local();
	private S3 s3 = new S3();
	private TipoStorage tipo=TipoStorage.LOCAL;//Tipo de Storage Enum
	
	public enum TipoStorage {
		LOCAL,
		S3
	}
	
	@Getter
	@Setter
	public class Local {
		
		private Path diretorioFotos;
		
	}
	
	@Getter
	@Setter
	public class S3 {
		
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private Regions regiao;//Adicionando o Region, que é da Amazon, ele traz uma enumeração e ajuda a escolher lá no application properties com ctrl + espaço
		private String diretorioFotos;
		
	}
	
	
	
}