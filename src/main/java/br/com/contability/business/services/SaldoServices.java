package br.com.contability.business.services;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	public Object getSaldoDo(Usuario usuario, LocalDate localDate) {

		BigDecimal saldoUsuario = super.getJpa().getSaldoDo(usuario.getId(), localDate.getMonthValue(),
				localDate.getYear());

		return saldoUsuario;
	}

	public Object getSaldoProvavelDo(Usuario usuario, LocalDate localDate) {

		BigDecimal saldoUsuario = super.getJpa().getSaldoProvavelDo(usuario.getId(), localDate.getMonthValue(),
				localDate.getYear());

		return saldoUsuario;
	}
	
	public Saldo getSaldo(Long idUsuario, LocalDate localDate) {
		return super.getJpa().getSaldo(idUsuario, localDate.getMonthValue(), localDate.getYear());
	}

}
