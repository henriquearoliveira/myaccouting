package br.com.contability.configuracao;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.contability.business.services.UsuarioServices;

@Component
public class AgendadorTarefas {

	@Scheduled(fixedDelay = 900000)
	public void ativaServidor() {

		new UsuarioServices().getJpa().findAll();

	}

}
