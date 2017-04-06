package br.com.contability.business.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contability.business.Cadastro;
import br.com.contability.business.Usuario;
import br.com.contability.utilitario.CaixaDeFerramentas;
import br.com.contability.utilitario.email.IEnviaEmail;

@Service
public class CadastrarServices {

	@Autowired
	private CodigoUsuarioServices services;

	@Autowired
	private IEnviaEmail enviaEmail;

	/**
	 * @param cadastro
	 * @param usuario
	 */
	public void confirmaUsuario(Cadastro cadastro, Usuario usuario, HttpServletRequest request) {

		String codigo = CaixaDeFerramentas.geraCodigo(6);
		services.insere(usuario, codigo);

		enviaEmail.envia("Ativação de cadastro no My Accounting", cadastro.getEmail(), codigo,
				CaixaDeFerramentas.configuraURLlogin(request));

	}

}
