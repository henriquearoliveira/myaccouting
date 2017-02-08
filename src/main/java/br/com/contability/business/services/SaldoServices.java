package br.com.contability.business.services;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import br.com.contability.business.Saldo;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.SaldoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;

@Service
public class SaldoServices extends ServicesAbstract<Saldo, SaldoRepository> implements IServices<Saldo> {

	/**
	 * @param usuario
	 * @return
	 */
	public Object getSaldoDo(Usuario usuario, Calendar calendar) {
		
		BigDecimal saldoUsuario = super.getJpa().getSaldoDo(usuario.getId(), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
		
		return saldoUsuario;
	}

	public Saldo getSaldo(Long idUsuario, Calendar calendar) {
		return super.getJpa().getSaldo(idUsuario, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
	}

}
