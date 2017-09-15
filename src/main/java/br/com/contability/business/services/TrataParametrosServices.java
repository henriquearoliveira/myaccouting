package br.com.contability.business.services;

import org.springframework.stereotype.Component;

import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;

@Component
public class TrataParametrosServices {
	
	/**
	 * @param id
	 * 
	 * EVITA EXCEÇÕES DE PARAMETROS INCORRETOS
	 * 
	 * @return
	 */
	public Long trataParametroLong(Object id) {
		
		String idLancamento = String.valueOf(id);
		
		Long idLan = 0l;
		
		try {
			
			idLan = Long.parseLong(idLancamento);
		} catch (Exception e) {
			throw new ObjetoInexistenteExceptionMessage("/lancamento", "Parametro incorreto");
		}
		return idLan;
	}

}
