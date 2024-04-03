package br.com.contability.relatorios;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface Relatorio {
	
	ModelAndView criaRelatorio(Map<String, Object> parametros, String nomeRelatorio);
}
