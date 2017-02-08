package br.com.contability.business.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Usuario;
import br.com.contability.business.facade.SaldoFacade;
import br.com.contability.business.repository.LancamentoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Service
public class LancamentoServices extends ServicesAbstract<Lancamento, LancamentoRepository>
		implements IServices<Lancamento> {
	
	@Autowired
	private SaldoFacade saldoFacade;
	
	/**
	 * @param lancamento
	 * @param usuario
	 */
	public void grava(Lancamento lancamento, Usuario usuario) {
		
		lancamento.setUsuario(usuario);
		
		BigDecimal bigDecimal = CaixaDeFerramentas.converteStringToBidDecimal(lancamento.getValorConversao());
		lancamento.setValorLancamento(bigDecimal);
		
		if (super.insere(lancamento) != null)
			saldoFacade.atualizaSaldoUsuario(usuario, lancamento);
		
	}

	/**
	 * @param usuario
	 * @param calendar
	 * @return
	 */
	public List<Lancamento> seleciona(Usuario usuario, Calendar calendar) {
		return super.getJpa().selecionaLancamentos(usuario.getId(), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
	}

	/**
	 * @param usuario
	 * @param dataHoraLancamento
	 * @return saldo
	 */
	public BigDecimal getSaldo(Usuario usuario, Calendar calendar) {
		return super.getJpa().getSaldo(usuario.getId(), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
	}

}
