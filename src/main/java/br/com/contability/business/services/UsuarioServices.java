package br.com.contability.business.services;

import org.springframework.stereotype.Service;

import br.com.contability.business.Cadastro;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.UsuarioRepository;
import br.com.contability.comum.ServicesAbstract;

@Service
public class UsuarioServices extends ServicesAbstract<Usuario, UsuarioRepository>{

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
	public void insere(Cadastro cadastro) {
		
		Usuario usuario = new Usuario();
		usuario.getUsuarioByCadastro(cadastro);
		
		super.insere(usuario, null);
	}

}
