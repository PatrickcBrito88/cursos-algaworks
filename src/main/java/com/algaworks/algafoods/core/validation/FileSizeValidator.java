package com.algaworks.algafoods.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{ 
									//1º Parâmetro, Esse validador valida quem ?
									//2º Parâmetro, Esse validator valida que tipo de dado ???
	
	private DataSize maxSize;
	//Representa um tamanho de dado. Através dele conseguiremos fazer um parse. OU seja de 1MB para tantos bytes
	//A forma do input não importa
	
	//Metodo para iniciar a validação
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.maxSize=DataSize.parse(constraintAnnotation.max());
		//Max é o atributo lá da anotação. Ou seja, ele vai pegar aquele max e 
		//vai converter par ao maxsize para fazer a validação no método de baixo
	}
	
	
	//Metodo obrigatório
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		//Value é o arquivo qu foi feito upload
		
		return value == null || value.getSize() <= this.maxSize.toBytes();
		
		//Se o arquivo for nulo (ou seja não mandou arquivo) Está true
		//Se o value.getSize (tamanho do arquivo enviado) for menor ou igual ou maxSize.toBytes (parametro que eu 
		//utilizei na anotação, também está Ok. Se não estiver ok, retorno false
		
		
	}
	
	
	
	
}
