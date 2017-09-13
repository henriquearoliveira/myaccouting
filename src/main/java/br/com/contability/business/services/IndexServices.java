package br.com.contability.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.contability.business.Usuario;

@Component
public class IndexServices {
	
	@Autowired
	private LancamentoServices lancamentoServices;

	public List<String> getBalanceteMeses(Usuario usuario) {
		
		List<String> meses = lancamentoServices.selecionaMesesDosLancamentosAnoAtual(usuario);
		
		return meses;
	}

	public List<Long> getBalanceteReceitas() {

		return null;
	}

	public List<Long> getBalanceteDespesas() {

		return null;
	}

}
