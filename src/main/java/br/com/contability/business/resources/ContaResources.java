package br.com.contability.business.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Conta;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.ContaServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;

@Controller
@RequestMapping("/conta")
public class ContaResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private ContaServices contaServices;

	@RequestMapping()
	public ModelAndView novo(Model model, Conta conta){
		model = ModelConstruct.setAttributes(model, "activeLiConta", "activeNovo");
		
		auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("conta/Conta");
		
		return mv;
	}
	
	@RequestMapping(value = "/{id}")
	public ModelAndView get(@PathVariable Long id, Model model, Conta conta){
		model = ModelConstruct.setAttributes(model, "activeLiConta", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("conta/Conta");
		
		contaServices.atualizaConta(usuario, mv, id, model);
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView novo(@Valid Conta conta, BindingResult result, RedirectAttributes attributes, Model model){
		if(result.hasErrors())
			return novo(model, conta);
		
		Usuario usuario = auth.getAutenticacao();
		
		contaServices.gravaConta(usuario, conta);
		
		attributes.addFlashAttribute("mensagem", "Conta salva com sucesso");
		return new ModelAndView("redirect:/conta");
	}
	
	@RequestMapping(value = "lista")
	public ModelAndView lista(Model model){
		
		model = ModelConstruct.setAttributes(model, "activeLiConta", "activeListagem");
		
		Usuario usuario = auth.getAutenticacao();
		
		List<Conta> contas = contaServices.seleciona(usuario);
		
		ModelAndView mv = new ModelAndView("conta/Listagem");
		mv.addObject("contas", contas);
		
		return mv;
	}
	
	@RequestMapping(value="/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remove(@PathVariable Long id){
		
		Usuario usuario = auth.getAutenticacao();
		
		contaServices.removeConta(usuario, id);
		
		return ResponseEntity.ok().build();
	}
	
}
