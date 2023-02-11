package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafoods.core.validation.FileContentType;
import com.algaworks.algafoods.core.validation.FileSize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@ApiModelProperty(hidden=true)
	@NotNull
	@FileSize(max= "1MB")
	@FileContentType (allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	private MultipartFile arquivo;
	
	//Por padrão, cada multipartfile só pode ter 1 MB
	
	//A requisição completa por ter no máximo 10MB por padrão
	
	//Isso é alterado no application properties
	
	@ApiModelProperty(value="Descrição da foto do produto", required=true)
	@NotBlank
	private String descricao;
	
}
