package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.UsuarioModel;
import com.algaworks.algafoods.api.model.input.SenhaInput;
import com.algaworks.algafoods.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafoods.api.model.input.UsuarioInput;
import com.algaworks.algafoods.domain.model.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuariosControlerOpenApi {
	
	@ApiOperation("Listar todos os usuários")
	CollectionModel<UsuarioModel> listar();
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "Id do usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Buscar um usuário pelo ID")
	UsuarioModel buscar(@ApiParam(value="Id do usuário", example="1", required=true) Long usuarioId);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 201, message = "Usuário cadastrado", response = Problem.class)
	})
	@ApiOperation("Adicionar um novo usuário")
	UsuarioModel adicionar(@ApiParam(name="corpo", value="Representação de um usuário com senha") 
		UsuarioComSenhaInput usuarioInput);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Usuário atualizado", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Atualizar um usuário pelo ID")
	UsuarioModel atualizar(@ApiParam(value="Id do usuário", example="1", required=true) Long usuarioId,
			@ApiParam(name="corpo", value="Representação de um novo usuário") UsuarioInput usuarioInput);
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 204, message = "Senha alterada", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	@ApiOperation("Alterar a senha de um usuário")
	void alterarSenha(@ApiParam(value="Id do usuário", example="1", required=true)Long usuarioId,
			@ApiParam(name="corpo", value="Representação de uma nova senha") SenhaInput senha);
}
