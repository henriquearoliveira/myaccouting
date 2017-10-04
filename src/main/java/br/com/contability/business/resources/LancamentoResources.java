package br.com.contability.business.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
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
	
	@GetMapping("/import")
	public ModelAndView novoFileImport(Model model, Lancamento lancamento) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovoImport");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/LancamentoImportFile");
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
			Model model, HttpSession session) {
		
		Usuario usuario = auth.getAutenticacao();
		
		if (result.hasErrors())
			return novo(model, lancamento);
		
		lancamentoServices.grava(lancamento, usuario, session);

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
	
	@GetMapping("/vencidos")
	public ModelAndView vencidos(Model model, Lancamento lancamento) {
		
		Usuario usuario = auth.getAutenticacao();

		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagemVencidos");
		
		List<Lancamento> lancamentosVencidos = lancamentoServices.selecionaVencidosAnteriorA(usuario, LocalDate.now());
		
		List<Lancamento> lancamentosVencidosDintinctosPorData = lancamentosVencidos.stream()
				.filter(CaixaDeFerramentas.distinctByKey(Lancamento::getDataHoraVencimento)).collect(Collectors.toList());
														// É OGRIGADO A USAR ASSSIM DEVIDO ESTAR EM OUTRA CLASSE
		
		ModelAndView mv = new ModelAndView("lancamento/ListagemVencidos");
		mv.addObject("lancamentosVencidos", lancamentosVencidosDintinctosPorData);

		return mv;
	}
	
	/* APLICAR O SEGUINTE: QUANDO CLICAR EM TODAS OS LANÇAMENTOS ABRIR UMA LISTA COM ASPENAS AS PENDENTES
	 * E EM CIMA UM <SELECT> PREENCHIDO COM AS DATAS QUE CONTEM CONTAS VENCIDAS. ACREDITO QUE VAI FICAR LEGAL */

	@GetMapping("/tabela")
	public String mostraTabelaCadastrados(Model model, @RequestParam("date") String calendarString,
			@RequestParam(value = "mobile", required = false) String mobile) {
		
		Usuario usuario = auth.getAutenticacao();

		LocalDate localDate = CaixaDeFerramentas.calendarFromStringMesAnoDate(calendarString);
		
		List<Lancamento> listaLancamentos = lancamentoServices.seleciona(usuario, localDate);
		
		if (listaLancamentos.isEmpty()) {
			return "lancamento/TabelasVazias :: listaVazia";
		}
		
		model.addAttribute("lancamentos", listaLancamentos);
		model.addAttribute("saldo", saldoServices.getSaldoDo(usuario, localDate));
		model.addAttribute("saldoProvavel", saldoServices.getSaldoProvavelDo(usuario, localDate));
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";

	}
	
	@GetMapping("/tabelaVencidos")
	public String mostraTabelaVencidos(Model model, @RequestParam("dataVencido") String calendarString,
			@RequestParam(value = "mobile", required = false) String mobile) {
		
		Usuario usuario = auth.getAutenticacao();

		LocalDate localDate = CaixaDeFerramentas.calendarFromStringDiaMesAnoDate(calendarString);
		
		List<Lancamento> listaLancamentos = lancamentoServices.selecionaVencidosDa(usuario, localDate);
		
		if (listaLancamentos.isEmpty()) {
			return "lancamento/TabelasVazias :: listaVazia";
		}
		
		model.addAttribute("lancamentos", listaLancamentos);
		model.addAttribute("total", listaLancamentos == null ? null : listaLancamentos.stream()
				.map(l -> l.getValorLancamento()).reduce(BigDecimal.ZERO, BigDecimal::add)); // MANEIRA DIFERENTE DE SOMAR
																							 // FAÇO COM O MAPTOLONG NO INDEX RESOURCEs
					// NO FUNCTION <T, R> O PRIMEIRO É O TIPO QUE ELE VAI TRABALHAR, E O SEGUNDO É
					// O QUE ELE VAI RETORNAR
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";

	}
	
	/* ENTENDENDO O STREAM
	 * Function<Lancamento, LocalDate> func = l -> l.getDataHoraVencimento();*/
	
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
	
	/*public void teste () {
		
		List<Integer> list = new ArrayList<>();
		
		avaliaExpressao(list, (n)-> n > 5 && n < 10);
	
	}
	   

	public void avaliaExpressao(List<Integer> list, Predicate<Integer> predicate) {
	    list.forEach(n -> {
	      if(predicate.test(n)) {
	            System.out.println(n + " ");
	        }
    });*/
	    
}
