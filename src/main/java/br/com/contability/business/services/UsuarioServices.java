package br.com.contability.business.services;

import org.springframework.stereotype.Service;

import br.com.contability.business.Usuario;
import br.com.contability.business.repository.UsuarioRepository;
import br.com.contability.comum.ServicesAbstract;

@Service
public class UsuarioServices extends ServicesAbstract<Usuario, UsuarioRepository>{

	public Usuario getPelo(String email) {
		
		Usuario usuario = super.getJpa().getPelo(email);
		
		return usuario;
	}

}
