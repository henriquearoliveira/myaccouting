package br.com.contability.business.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.LancamentoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Service
public class LancamentoServices extends ServicesAbstract<Lancamento, LancamentoRepository>
		implements IServices<Lancamento> {

	/**
	 * @param lancamento
	 * @param usuario
	 */
	public void grava(Lancamento lancamento, Usuario usuario) {
		
		lancamento.setUsuario(usuario);
		
		System.out.println(lancamento.getValorConversao());
		BigDecimal bigDecimal = CaixaDeFerramentas.converteStringToBidDecimal(lancamento.getValorConversao());
		lancamento.setValorLancamento(bigDecimal);
		
		super.insere(lancamento);
		
	}

}
