package com.algaworks.algafoods.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{

	
	private int numeroMultiplo;
	
	
	//Metodo para iniciar a validação
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo=constraintAnnotation.numero();//Pegou o numero que veio pela @Anotation
		//ConstraintAnnotation, ou seja pega o parâmetro que eu coloquei na constraint
	}
	
	
	//Metodo obrigatório
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido=true;
		
		//NumeroMultiplo é o que eu coloquei como base para saber se é multiplo por ele
		//valorDecimal  ou number é o número que o usuário está tentando passar
		//Value pega o numero que o usuário escreveu
		
		if (value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal);//para saber o resto
			valido=BigDecimal.ZERO.compareTo(resto)==0;//Compara se o valor é zero e retorno bool
		}
		
		
		return valido;
	}
	//Number abrange todos os tipos de número
	/* É uma classe de validação de Multiplo
	 * que suporta qualquer Number
	 * 
	 */
	
	
	
}
