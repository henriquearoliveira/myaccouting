package br.com.contability.business.resources;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.comum.AuthenticationAbstract;

@Controller
@RequestMapping
public class IndexResources {
	
	@Autowired
	private AuthenticationAbstract auth;

	@RequestMapping("/index")
	public String loginSucesso(HttpSession session){
		
		session.setAttribute("user", auth.getAutenticacao().getEmail());
		
		return "Index";
		
	}
	
	@RequestMapping()
	public String loginPrincipal(){
		
		return "Index";
		
	}
	
	/*@RequestMapping("/index/{id}")
	public String loginSucesso(@ModelAttribute("id") Usuario usuario, Model model){ // ModelAttribute é muito bom
		
		if (usuario.getNome() == null)
			throw new ObjetoInexistenteException("Objeto não encontrado", "Index");
		
		return "Index";
		
	}*/
	
	
	
}
