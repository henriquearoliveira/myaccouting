package br.com.contability.business.resources;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.EsqueceuSenha;

@Controller
@RequestMapping("/esqueceusenha")
public class EsqueceuSenhaResources {
	
	@GetMapping
	public ModelAndView requisitaEmail(EsqueceuSenha esqueceuSenha){
		
		ModelAndView mv = new ModelAndView("EsqueceuSenha");
		
		return mv;
		
	}
	
	@PostMapping
	public ModelAndView confirmaEmail(@Valid EsqueceuSenha esqueceuSenha, RedirectAttributes redirect){
		
		
		
		return null;
	}

}
