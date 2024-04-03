package br.com.contability.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalheErro {

    private String titulo;

    @JsonInclude(Include.NON_NULL)
    private String mensagemDesenvolvedor;

    private Long status;
    private Long timestamp;
}
