package br.com.contability.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.contability.business.services.UsuarioServices;

@Component
public class AgendadorTarefas {

	@Autowired
	private UsuarioServices usuarioServices;

	@Scheduled(fixedDelay = 900000)
	public void ativaServidor() {

		usuarioServices.getJpa().findAll();

	}

}
