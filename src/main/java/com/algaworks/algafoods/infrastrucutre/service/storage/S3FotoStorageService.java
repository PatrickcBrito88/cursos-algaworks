package com.algaworks.algafoods.infrastrucutre.service.storage;

import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.core.storage.StorageProperties;
import com.algaworks.algafoods.domain.exception.StorageException;
import com.algaworks.algafoods.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3FotoStorageService implements FotoStorageService {

	// Tem que colocar a dependência no POM.
	// Endereço:
	// https://search.maven.org/artifact/com.amazonaws/aws-java-sdk-s3/1.12.233/jar

	// Tem que criar um Bean para usar o amazonS3 (Criado em AmazonS3Config)

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

			var objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(novaFoto.getContentType());

			var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo, // Caminho
																												// do
																												// arquivo
					novaFoto.getInputStream(), // InputStream
					objectMetaData)// Metadados
			 .withCannedAcl(CannedAccessControlList.PublicRead);//Permissao de leitura
			// pública. Sem isso, a URL do objeto armazenado não pode ser acessado por
			// ninguém

			amazonS3.putObject(putObjectRequest);

		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	public String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url=amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		return FotoRecuperada.builder()
				.url(url.toString()).build();
		
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
					caminhoArquivo);

			amazonS3.deleteObject(deleteObjectRequest);

		} catch (Exception e) {
			throw new StorageException("Não foi possível deletar o arquivo!", e);
		}
		
		
	}
}
