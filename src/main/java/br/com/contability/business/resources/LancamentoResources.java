package br.com.contability.business.resources;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import br.com.contability.comum.StringPaginasAndRedirect;
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
	public ModelAndView novo(Model model, Lancamento lancamento) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();

		ModelAndView mv = new ModelAndView("lancamento/Lancamento");
		mv.addObject("categorias", categoriaServices.seleciona(usuario));
		mv.addObject("contas", contaServices.seleciona(usuario));

		return mv;

	}

	@GetMapping("/{idLancamento}")
	public ModelAndView get(@PathVariable Object idLancamento, Model model) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");

		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");

		return lancamentoServices.getLancamento(usuario, mv, idLancamento);

	}

	@PostMapping
	public ModelAndView salvar(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attributes,
			Model model) {
		
		Usuario usuario = auth.getAutenticacao();
		
		if (result.hasErrors())
			return novo(model, lancamento);
		
		lancamentoServices.grava(lancamento, usuario);

		attributes.addFlashAttribute("mensagem", "Lancamento gravado com sucesso");
		return new ModelAndView(StringPaginasAndRedirect.LANCAMENTO);
	}

	@GetMapping("/lista")
	public ModelAndView lista(Model model, Lancamento lancamento) {
		auth.getAutenticacao();

		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagem");

		ModelAndView mv = new ModelAndView("lancamento/Listagem");

		return mv;
	}

	@GetMapping("/tabela")
	public String mostraTabelaCadastrados(Model model, @RequestParam("date") String calendarString,
			@RequestParam(value = "mobile", required = false) String mobile) {
		
		Usuario usuario = auth.getAutenticacao();

		LocalDate localDate = CaixaDeFerramentas.calendarFromStringDate(calendarString);

		model.addAttribute("lancamentos", lancamentoServices.seleciona(usuario, localDate));
		model.addAttribute("saldo", saldoServices.getSaldoDo(usuario, localDate));
		model.addAttribute("saldoProvavel", saldoServices.getSaldoProvavelDo(usuario, localDate));
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";

	}
	
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.remove(usuario, id);
		
		return ResponseEntity.ok().build();
	}

	/*
	 * APENAS TESTE MESMO Teste teste = Teste.porString("Primeiro");
	 * System.out.println(teste.name());
	 */

}
