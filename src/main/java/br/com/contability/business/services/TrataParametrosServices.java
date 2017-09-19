package br.com.contability.business.services;

import org.springframework.stereotype.Component;

import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;

@Component
public class TrataParametrosServices {
	
	/**
	 * @param id
	 * 
	 * EVITA EXCEÇÕES DE PARAMETROS INCORRETOS
	 * 
	 * @return Long
	 */
	public Long trataParametroLong(Object id, String redirecionamento) {
		
		String idLancamento = String.valueOf(id);
		
		Long idLan = 0l;
		
		try {
			
			idLan = Long.parseLong(idLancamento);
		} catch (Exception e) {
			throw new ObjetoInexistenteExceptionMessage(redirecionamento, "Parametro incorreto");
		}
		return idLan;
	}
	
	/**
	 * @param id
	 * @return Long
	 */
	public Long trataParametroLongAPI(Object id) {
		
		String idLancamento = String.valueOf(id);
		
		Long idLan = 0l;
		
		try {
			
			idLan = Long.parseLong(idLancamento);
		} catch (Exception e) {
			throw new ObjetoInexistenteException("Parametro incorreto");
		}
		return idLan;
	}

}
