package com.algaworks.algafoods.core.validation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {
	// 1º Parâmetro, Esse validador valida quem ?
	// 2º Parâmetro, Esse validator valida que tipo de dado ???

	private List<String> allowedContentTypes;

	// Metodo para iniciar a validação
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
		// Maneira de copiar uma lista

		// Aqui sempre vai ter o constraintAnnotation
		// Eu atribuo o que vem no constraint para dentro do meu método global
	}

	// Metodo obrigatório
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		
		return value == null ||
				this.allowedContentTypes.contains(value.getContentType());

	}

}
