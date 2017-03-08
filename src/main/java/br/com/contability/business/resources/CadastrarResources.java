package br.com.contability.business.resources;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarResources {

	@GetMapping
	public ModelAndView novo(Cadastro cadastro){
		
		ModelAndView mv = new ModelAndView("Cadastrar");
		
		return mv;
	}
	
	@PostMapping()
	public ModelAndView cadastrar(@Valid Cadastro cadastro, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors())
			novo(cadastro);
		
		attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso, por favor visualize o email enviado");
		
		return new ModelAndView("redirect:/login");
	}
	
}
