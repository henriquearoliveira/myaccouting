package br.com.contability.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contability.business.Cadastro;
import br.com.contability.utilitario.CaixaDeFerramentas;
import br.com.contability.utilitario.email.IEnviaEmail;

@Service
public class CadastrarServices {
	
	@Autowired
	private IEnviaEmail enviaEmail;

	public void cadastraUsuario(Cadastro cadastro) {
		
		String codigo = CaixaDeFerramentas.geraCodigo(6);
		enviaEmail.envia("Ativação de cadastro no My Accounting", cadastro.getEmail(), codigo, "http://localhost:9090/login/requestcode?codigo=");
		
		
	}
	
	
	
}
