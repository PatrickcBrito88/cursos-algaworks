package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.CidadeController;
import com.algaworks.algafoods.api.controller.EstadoController;
import com.algaworks.algafoods.api.model.CidadeModel;
import com.algaworks.algafoods.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{
	
	//Extendendo RepresentationModelAssembler poderemos incluir os links direto na classe de 
	//assembler	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	//Tem que implementar o construtor
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
		//Especifico qual o controlador que gerencia cidades e qual a classe que será o produto
		//desta implementação
		
	}
	
	@Override//Este método já existe na classe pai
	public CidadeModel toModel (Cidade cidade) {
		
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		//Link para id do Estado
		cidadeModel.add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
		
		//Link collections de cidade
		cidadeModel.add(algaLinks.linkToCidade("cidades"));
		
		return cidadeModel;
	}	
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToCidades());
		//Este método é sobreescrito apenas para incluir o link das cidades no rodapé
		
	}
	
	
	//Não precisa fazer o stream para collect pq a classe pai já tem o CollectionModel
//	public List<CidadeModel> toCollectModel (List<Cidade> cidades){
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//		/*
//		 * Explicação do porquê do .map e do .collect
//		 * 
//		 * Stream é uma bilbioteca inserida a partir do Java 8
//		 * A partir dele podemos chamar métodos intermediários e métodos finais.
//		 * O map serve para transformação de dados.
//		 * Neste caso específico está transformando cidade em cidadeModel.
//		 * A lista de cidades continuará sendo lista de cidades, mas eu preciso retornar uma lisya
//		 * de cidades Model.
//		 * Desta forma o .map transforma a lista de cidades em cidadesModel
//		 * chama o collect e chama o Collectors para transformar o resultado da transformação
//		 * em uma nova lista, que serve de retorno para o método. 
//		 */
//		
//		
//	}
}
