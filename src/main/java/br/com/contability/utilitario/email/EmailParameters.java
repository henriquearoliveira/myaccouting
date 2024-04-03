package br.com.contability.utilitario.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailParameters {

    private String assunto;
    private String para;
    private String codigo;
    private String url;
    private String templateEmail;
}
