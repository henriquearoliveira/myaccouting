package br.com.contability.business.resources;

import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CategoriaServices;
import br.com.contability.business.services.ContaServices;
import br.com.contability.business.services.LancamentoServices;
import br.com.contability.business.services.SaldoServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;
import br.com.contability.utilitario.CaixaDeFerramentas;

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
	
	@Autowired
	private SaldoServices saldoServices;
	
	@GetMapping()
	public ModelAndView novo(Model model, Lancamento lancamento){
		model = ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");
		mv.addObject("categorias", categoriaServices.seleciona(usuario));
		mv.addObject("contas", contaServices.seleciona(usuario));
		
		return mv;
		
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable Long id, Model model){
		model = ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");
		
		return lancamentoServices.getLancamento(usuario, mv, id, model);
		
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attributes, Model model){
		Usuario usuario = auth.getAutenticacao();
		
		if(result.hasErrors())
			return novo(model, lancamento);
		
		lancamentoServices.grava(lancamento, usuario);
		
		attributes.addFlashAttribute("mensagem", "Lancamento gravado com sucesso");
		return new ModelAndView("redirect:/lancamento");
	}
	
	@GetMapping("/lista")
	public ModelAndView lista(Model model, Lancamento lancamento){
		auth.getAutenticacao();
		
		model = ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagem");
		
		ModelAndView mv = new ModelAndView("lancamento/Listagem");
		
		return mv;
	}
	
	@GetMapping("/tabela/{date}")
	public String mostraTabelaCadastrados(Model model, @PathVariable("date") String calendarString){
		
		Usuario usuario = auth.getAutenticacao();
		
		Calendar calendar = CaixaDeFerramentas.calendarFromStringDate(calendarString);
		
		model.addAttribute("lancamentos", lancamentoServices.seleciona(usuario, calendar));
		model.addAttribute("saldo", saldoServices.getSaldoDo(usuario, calendar));
		
		return "lancamento/Tabela :: tabelaLancamento";
		
	}
	
	/* APENAS TESTE MESMO
	Teste teste = Teste.porString("Primeiro");
	System.out.println(teste.name());
	*/

}
