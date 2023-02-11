package com.algaworks.algafoods.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

public class ValorZeroIncluiDescricaoValidator implements 
ConstraintValidator<ValorZeroIncluiDescricao, Object>{

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraint) {
		// TODO Auto-generated method stub
		this.valorField=constraint.valorField();
		this.descricaoField=constraint.descricaoField();
		this.descricaoObrigatoria=constraint.descricaoObrigatoria();
	}
	
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		/*Neste caso por exemplo o objetoValidação é o restaurante
		 * É sempre quem foi validado
		 */
				
		boolean valido = true;
		
		/*O BeanUtils permite invocar o .get do atributo
		 * o getProperty pega o objeto. Nesta classe utilizamos o Object para usar o polimorfismo
		 * Ou seja, usando em restaurante será um objeto de restautrante. 
		 * Sem seguida passamos o Field que representa lá na anotação o field que eu quero pegar.
		 * Ou seja, quero taxaFrete. Lá na classe falei que o valorField=taxaFrete
		 */
		try {
			BigDecimal valor = (BigDecimal)BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(),
					valorField).getReadMethod().invoke(objetoValidacao);
			
			String descricao = (String)BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(),
					descricaoField).getReadMethod().invoke(objetoValidacao);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor)==0
					&& descricao != null) {
				valido=descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			/* 
			 * Se o valor é diferente de nulo e é igual a 0(zero) e a descricao é diferente de nulo
			 * valido recebe o resultado de (descricao contem descricaoObrigatoria(frete gratis))
			 */
			
			
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		
		return valido;
	}
	
	

}
