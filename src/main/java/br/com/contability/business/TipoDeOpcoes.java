package br.com.contability.business;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum TipoDeOpcoes {

    DEPOSITO_CONTA("Deposito em conta")/*, LANCAMENTO_PROXIMO_MES("Lançamento próximo mês")*/;

    private String descricao;

    TipoDeOpcoes(String descricao) {
        this.descricao = descricao;
    }
}
