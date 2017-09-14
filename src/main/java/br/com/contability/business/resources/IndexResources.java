package br.com.contability.business.resources;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.services.IndexServices;
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
	
	@Autowired
	private IndexServices indexServices;

	@GetMapping("/index")
	public ModelAndView loginSucesso(HttpSession session) {

		configuraSession(session);
		
		ModelAndView mv = new ModelAndView("Index");

		mv.addObject("meses", indexServices.getBalanceteMeses(auth.getAutenticacao()));
		mv.addObject("receitas", indexServices.getBalanceteReceitas());
		mv.addObject("despesas", indexServices.getBalanceteDespesas());
		mv.addObject("receitasDoMes", indexServices.getReceitasMes());
		mv.addObject("despesasDoMes", indexServices.getDespesasMes());
		mv.addObject("saldoDoMes", indexServices.getSaldoMes());
		mv.addObject("contas", indexServices.getContas(auth.getAutenticacao()));
		
		return mv;

	}

	/*private void teste() {

		Map<String, Long> barChartData = new HashMap<>();
		barChartData.put("Samsung", 5000L);
		barChartData.put("Iphone", 10000L);
		barChartData.put("MI", 2000L);
		barChartData.put("Lava", 4000L);
		barChartData.put("Oppo", 3560L);
		barChartData.put("HTC", 5560L);

		System.out.println(barChartData.keySet());
		System.err.println("hahaha");
		System.out.println(barChartData.values());

	}*/

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
