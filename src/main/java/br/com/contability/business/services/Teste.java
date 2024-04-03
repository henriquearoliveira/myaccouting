package br.com.contability.business.services;

import br.com.contability.exceptions.ObjetoInexistenteException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Teste {

    PRIMEIRO_VALOR("Primeiro"), SEGUNDO_VALOR("Segundo");

    private final String valor;

    public static Teste porString(String valor) {
		return Arrays.stream(Teste.values()).filter(teste -> teste.getValor().equals(valor)).findFirst().orElseThrow(ObjetoInexistenteException::new);
    }
}
