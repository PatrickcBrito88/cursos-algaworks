package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class RestauranteModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
		
	}
	
	/*Metodo para copiar o restauranteeditao para o novo restaurante
	 * Onde não precisa colocar os atributos que não serão copiados.
	 */
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		/*
		 * Gatilho para quando for alterar o id da Cozinha na atualização de restaurante
		 */
		restaurante.setCozinha(new Cozinha());
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
