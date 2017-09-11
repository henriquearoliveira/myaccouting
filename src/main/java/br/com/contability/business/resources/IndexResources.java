package br.com.contability.business.resources;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.comum.AuthenticationAbstract;

/**
 * @author eliel
 *
 */
@Controller
@RequestMapping
public class IndexResources {

	@Autowired
	private AuthenticationAbstract auth;

	@GetMapping("/index")
	public String loginSucesso(HttpSession session) {

		configuraSession(session);

		return "Index";

	}

	/**
	 * @param session
	 */
	private void configuraSession(HttpSession session) {
		session.setAttribute("userEmail", auth.getAutenticacao().getEmail());
		session.setAttribute("userUrl", auth.getAutenticacao().getUploadImage() == null ? null
				: auth.getAutenticacao().getUploadImage().getSecureUrl());
		session.setAttribute("userDate", auth.getAutenticacao().getDataHoraCadastro());
	}

	@GetMapping()
	public String loginPrincipal(HttpSession session) {

		configuraSession(session);

		return "Index";

	}

	/*
	 * @RequestMapping("/index/{id}") public String
	 * loginSucesso(@ModelAttribute("id") Usuario usuario, Model model){ //
	 * ModelAttribute é muito bom // o model atribute já pega o usuario direto. if
	 * (usuario.getNome() == null) throw new
	 * ObjetoInexistenteException("Objeto não encontrado", "Index");
	 * 
	 * return "Index";
	 * 
	 * }
	 */

}
