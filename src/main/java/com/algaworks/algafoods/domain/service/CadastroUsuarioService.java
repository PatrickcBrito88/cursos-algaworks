package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafoods.api.assembler.UsuarioModelDisassembler;
import com.algaworks.algafoods.api.model.UsuarioModel;
import com.algaworks.algafoods.api.model.input.SenhaInput;
import com.algaworks.algafoods.api.model.input.UsuarioInput;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafoods.domain.model.Grupo;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		/*Para realizar o procedimento de consulta de e-mail tem que tirar o gerenciamento do JPA sobre a instância
		 * Se não ele sincroniza antes de fazer a cosulta de e-mail por exemplo. Aí quando faz o findByEmail
		 * Ele considera que tem 2 e-mails cadastrados.
		 * Procedimento para tirar o gerenciamento abaixo:
		 * Declara um entityManager e dá um manager.detach(usuario)
		 */
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException
				(String.format("Já existe usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if (usuario.senhaNaoCoincindeCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(novaSenha);
	}

	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public void associarGrupo (Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		usuario.associar(grupo);		
	}
	
	@Transactional
	public void desassociarGrupo (Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		usuario.desassociar(grupo);		
	}
}
