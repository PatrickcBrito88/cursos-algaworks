package com.algaworks.algafoods.infrastrucutre.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafoods.core.storage.StorageProperties;
import com.algaworks.algafoods.domain.exception.StorageException;
import com.algaworks.algafoods.domain.service.FotoStorageService;

//@Service
public class LocalFotoStorageService implements FotoStorageService{

	//Injetando o valor de uma propriedade do applicationproperties
	//@Value("${algafood.caminho.storage}")
	private Path diretorioFotos = Path.of("/Users/patri/OneDrive/Área de Trabalho/catalogo");

	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
		try {
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
			//Copio da entrada e jogo na saída. É o mesmo que transfereTo do Multipart
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar aquivo.", e);
		}
		
	}
	
	private Path getArquivoPath (String nomeArquivo) {
		System.out.println(storageProperties.getLocal().getDiretorioFotos());
		System.out.println(diretorioFotos);
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
		//O Path precisa de um local + o nome do arquivo. 
		//O Resolve concatena o diretório de fotos com o nome do aquivo.
	}

	@Override
	public void remover(String nomeArquivo) {
		Path arquivo = getArquivoPath(nomeArquivo);//Pega o caminho completo do aquivo
		try {
			Files.deleteIfExists(arquivo);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir aquivo.", e);
		}
		
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
		Path arquivoPath = getArquivoPath(nomeArquivo);
		FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
				.inputStream(Files.newInputStream(arquivoPath))
				.build();
		
		
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar aquivo.", e);
		}
	}

}
