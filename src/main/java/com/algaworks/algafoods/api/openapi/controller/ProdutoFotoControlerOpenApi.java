package com.algaworks.algafoods.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.FotoProdutoModel;
import com.algaworks.algafoods.api.model.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface ProdutoFotoControlerOpenApi {
	
	
	
	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 200, message = "Foto do produto atualizada", response = Problem.class),
		@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
	})
	@ApiOperation("Atualizar uma Foto pelo Id do Restaurante e pelo Id do Produto")
	FotoProdutoModel atualizarFoto(
			@ApiParam(example="1", required=true) 
			Long restauranteId, 
			
			@ApiParam(example="1", required=true) 
			Long produtoId,
			
			FotoProdutoInput fotoProdutoInput,
			
			@ApiParam (value="Arquivo da foto do produto (máximo 500 KB, apenas JPG e PNG)",
				required=true) //Maneira de fazer o content type passar - Aula 18.36
			MultipartFile arquivo) throws IOException;
	
	
	
	
	
	
	
	
	@ApiOperation(value="Buscar uma Foto pelo Id do Restaurante e pelo Id do Produto", 
			produces="application/json, image/jpeg, image/png")
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
	})
	FotoProdutoModel buscar (
			@ApiParam(value = "ID do restaurante", example="1", required=true) //Não sei pq mas só funcionou depois que eu coloquei o value
			Long restauranteId,
			
			@ApiParam(value = "ID do produto", example="1", required=true)
			Long produtoId);
	

	@ApiOperation(value="Atualizar uma Foto pelo Id do Restaurante e pelo Id do Produto", hidden=true)
	ResponseEntity<?> servirFoto (@ApiParam(example="1", required=true)Long restauranteId, 
			@ApiParam(example="1", required=true)Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	
	
	
	@ApiResponses({ //Retornar mensagens específicas para diferentes tipos de erros
		@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class),
		@ApiResponse(code = 204, message = "Foto deletada", response = Problem.class)
	})
	@ApiOperation("Atualizar uma Foto pelo Id do Restaurante e pelo Id do Produto")
	void deletarFoto (@ApiParam(example="1", required=true)Long produtoId, @ApiParam(example="1", required=true)Long restauranteId);
	

}

	


