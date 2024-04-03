package br.com.contability.business.services;

import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoNaoAutorizadoException;
import org.springframework.stereotype.Component;

@Component
public class TrataParametrosServices {

    /**
     * @param id EVITA EXCEÇÕES DE PARAMETROS INCORRETOS
     * @return Long
     */
    public Long trataParametroLongMessage(Object id, String redirecionamento) {
        final String idLancamento = String.valueOf(id);
        try {

            return Long.parseLong(idLancamento);
        } catch (Exception e) {
            throw new ObjetoInexistenteExceptionMessage(redirecionamento, "Parametro incorreto");
        }
    }

    public Long trataParametroLongException(Object id) {
        final String idLancamento = String.valueOf(id);
        try {
            return Long.parseLong(idLancamento);
        } catch (Exception e) {
            throw new ObjetoNaoAutorizadoException("Parametro incorreto");
        }
    }

    public Long trataParametroLong(Object id) {
        final String idLancamento = String.valueOf(id);
        try {
            return Long.parseLong(idLancamento);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param id
     * @return Long
     */
    public Long trataParametroLongAPI(Object id) {
        final String idLancamento = String.valueOf(id);
        try {

            return Long.parseLong(idLancamento);
        } catch (Exception e) {
            throw new ObjetoInexistenteException("Parametro incorreto");
        }
    }

}
