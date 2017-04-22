package br.com.contability.business.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Saldo;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.LancamentoServices;
import br.com.contability.business.services.SaldoServices;

@Component
public class SaldoFacade {
	
	@Autowired
	private LancamentoServices lancamentoServices;
	
	@Autowired
	private SaldoServices saldoServices;

	/**
	 * @param usuario
	 * @param lancamento
	 */
	public void atualizaSaldoUsuario(Usuario usuario, Lancamento lancamento) {
		
		BigDecimal saldoValor = lancamentoServices.getSaldo(usuario, lancamento.getDataHoraLancamento());
		
		Saldo saldo = saldoServices.getSaldo(usuario.getId(), lancamento.getDataHoraLancamento());
		
		if (saldo == null){
			
			saldo = new Saldo();
			saldo.setDataHoraCadastro(LocalDateTime.now());
			saldo.setDataHoraLancamento(LocalDateTime.now());
			saldo.setSaldoAtual(saldoValor);
			saldo.setUsuario(usuario);
			
			saldoServices.insere(saldo, null);
		} else
			saldo.setSaldoAtual(saldoValor);
			saldoServices.atualiza(saldo, null);
		
	}

}
