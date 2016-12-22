package br.com.contability.business.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CategoriaServices;
import br.com.contability.business.services.ContaServices;
import br.com.contability.business.services.LancamentoServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;

@Controller
@RequestMapping("/lancamento")
public class LancamentoResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private CategoriaServices categoriaServices;
	
	@Autowired
	private ContaServices contaServices;
	
	@Autowired
	private LancamentoServices lancamentoServices;
	
	@RequestMapping()
	public ModelAndView novo(Model model, Lancamento lancamento){
		model = ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");
		mv.addObject("categorias", categoriaServices.seleciona(usuario));
		mv.addObject("contas", contaServices.seleciona(usuario));
		
		return mv;
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attributes, Model model){
		Usuario usuario = auth.getAutenticacao();
		
		if(result.hasErrors())
			return novo(model, lancamento);
		
		lancamentoServices.grava(lancamento, usuario);
		
		attributes.addFlashAttribute("mensagem", "Lancamento gravado com sucesso");
		return new ModelAndView("redirect:/lancamento");
	}
	
	@RequestMapping("/lista")
	public ModelAndView lista(Model model, Lancamento lancamento){
		auth.getAutenticacao();
		
		model = ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagem");
		
		ModelAndView mv = new ModelAndView("lancamento/Listagem");
		
		return mv;
	}
	
	@RequestMapping("/tabela/{date}")
	public String mostraTabelaCadastrados(Model model, @PathVariable("date") String calendar){
		
		System.out.println("passou");
		Usuario usuario = auth.getAutenticacao();
		
		model.addAttribute("lancamentos", lancamentoServices.seleciona(usuario));
		
		return "lancamento/Tabela :: tabelaLancamento";
		
	}

}
