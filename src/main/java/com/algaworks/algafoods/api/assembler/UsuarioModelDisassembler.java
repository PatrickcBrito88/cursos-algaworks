package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.input.UsuarioInput;
import com.algaworks.algafoods.domain.model.Usuario;

@Component
public class UsuarioModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toObjectModel (UsuarioInput usuarioInput) {
		return modelMapper
				.map(usuarioInput, Usuario.class);
	}
	
	public void copyToObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
