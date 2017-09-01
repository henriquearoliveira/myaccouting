package br.com.contability.business.resources;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Usuario;

@Controller
@RequestMapping("/perfil")
public class PerfilResources {

	@GetMapping
	public ModelAndView perfil(Usuario usuarioModel, Model model) {

		ModelAndView mv = new ModelAndView("perfil/Perfil");

		return mv;

	}

	@PostMapping("/edit")
	public ModelAndView perfilEdit() {

		return null;
	}

}
