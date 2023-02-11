package com.algaworks.algafoods.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.AlgaLinks;
import com.algaworks.algafoods.api.controller.UsuarioControler;
import com.algaworks.algafoods.api.controller.UsuariosGruposController;
import com.algaworks.algafoods.api.model.UsuarioModel;
import com.algaworks.algafoods.domain.model.Usuario;

@Relation(collectionRelation = "Usuários")
@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public UsuarioModelAssembler() {
		super(UsuarioControler.class, UsuarioModel.class);

	}

	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		
		usuarioModel.add(algaLinks.linkToUsuarios("usuários"));

		usuarioModel.add(algaLinks.linkToGrupoUsuarios(usuario.getId(), "grupos-usuários"));
						

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToUsuaios());
	}

//	public List<UsuarioModel> toCollectModel (Collection<Usuario> usuarios){
//		return usuarios.stream()
//				.map(usuario -> toModel(usuario))
//				.collect(Collectors.toList());
//	}

}
