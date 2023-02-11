package com.algaworks.algafoods.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafoods.api.model.CidadeResumoModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(value="Cep do Endereço", example="28970-000", required=true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(value="Logradouro", example="Rua Leogisa Marinho Moraes, Quadra 60 Lote 86", required=true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(value="Número do endereço", example="20", required=true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(value="Complemento do endereço", example="Casa")
	private String complemento;
	
	@ApiModelProperty(value="Bairro", example="Centro", required=true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;
	
}
