package br.com.contability.comum;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.contability.business.Lancamento;
import br.com.contability.business.services.LancamentoServices;

@Component
public class SessaoServices {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private LancamentoServices lancamentoServices;
	
	/**
	 * @param session
	 */
	public void configuraSession(HttpSession session) {
		
		session.setAttribute("userEmail", auth.getAutenticacao().getEmail());
		session.setAttribute("userUrl", auth.getAutenticacao().getUploadImage() == null ? null
				: auth.getAutenticacao().getUploadImage().getSecureUrl());
		session.setAttribute("userDate", auth.getAutenticacao().getDataHoraCadastro());
		session.setAttribute("lancamentosVencidos",
				lancamentoServices.selecionaVencidosAnteriorA(auth.getAutenticacao(), LocalDate.now()));
	}
	
	public void atualizaVencidos(HttpSession session, List<Lancamento> lancamentosVencidos) {
		session.setAttribute("lancamentosVencidos", lancamentosVencidos);
	}

}
