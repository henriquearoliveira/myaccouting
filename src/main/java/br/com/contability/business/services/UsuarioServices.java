package br.com.contability.business.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.contability.business.Cadastro;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.UsuarioRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;

@Service
public class UsuarioServices extends ServicesAbstract<Usuario, UsuarioRepository> {

	/**
	 * @param email
	 * @return
	 */
	public Usuario getPelo(String email) {

		Usuario usuario = super.getJpa().getPelo(email);

		return usuario;
	}

	/**
	 * @param cadastro
	 */
	public Usuario insere(Cadastro cadastro) {

		Usuario usuario = new Usuario();
		usuario.getUsuarioByCadastro(cadastro, false);

		return super.insere(usuario, null);
	}

	/**
	 * @param codigo
	 * @return
	 */
	public Usuario getUsuarioPelo(String codigo) {

		Optional<Usuario> usuario = super.getJpa().getUsuarioPelo(codigo);
		
		return usuario.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/login", "Codigo não encontrado por favor requisite outro codigo"));

	}

}
