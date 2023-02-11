package com.algaworks.algafoods.api.model;

import com.algaworks.algafoods.domain.model.Cidade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

	@ApiModelProperty(value="CEP do endereço do pedido", example = "28970-000")
	private String cep;
	
	@ApiModelProperty(value="Logradouro do Endereço do pedido", example = "Rua Leogisa Marinho Moraes, quadra 60, Lote 86")
	private String logradouro;
	
	@ApiModelProperty(value="Número do endereço do pedido", example = "10")
	private String numero;
	
	@ApiModelProperty(value="Complemento do endereço do pedido", example = "Casa")
	private String complemento;
	
	@ApiModelProperty(value="Bairro do endereço do pedido", example = "Centro")
	private String bairro;
	
	private CidadeResumoModel cidade;
	
//	public void setNomeEstadoDaCidade(String nome) {
//		cidade.setEstado(nome);
//	}
}
