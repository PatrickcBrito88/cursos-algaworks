package com.algaworks.algafoods.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoods.api.model.CidadeResumoModel;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.EnderecoModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.ItemPedidoInput;
import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Endereco;
import com.algaworks.algafoods.domain.model.ItemPedido;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	/*
	 * Bean para fazer injeção de modelmapper com autowired
	 * Uma vez que modelmapper não é uma dependência do Spring
	 */
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		/*customizar um mapeamento de correspondencia
		 * No exemplo abaixo colocamos o taxaFrete no RestauranteModel como preçofrete
		 * O Model mapper não consegue fazer a conversão, então mapeamos conforme abaixo
		 */
		
		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  // Método que faz com que o ModelMapper
		//pule o mapeaento do ID, pois o Id do itenspedido é automático
		
		//Este da aula não está funcionando
		var enderecoToEnderecoModelTypeMap=modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
			
		//Método alternativo proposto por um aluno -- Funcionando
//		modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
//	    .addMapping(Endereco::getNomeEstadoDaCidade, EnderecoModel::setNomeEstadoDaCidade);
//		//Houve alteração na classe endereco e enderecoModel para este funcionar
		
		return modelMapper;
	}

}
