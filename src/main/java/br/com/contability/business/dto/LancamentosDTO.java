package br.com.contability.business.dto;

import br.com.contability.business.Lancamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentosDTO {

    private List<Lancamento> lancamentos;
}
