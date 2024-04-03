package br.com.contability.business.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadastroDTO {

    @NotEmpty(message = "Não pode ser null")
    @NotNull(message = "Não pode ser vazio")
    private String nome;

	@NotEmpty(message = "Não pode ser null")
	@NotNull(message = "Não pode ser vazio")
	private String email;

	@NotEmpty(message = "Não pode ser null")
	@NotNull(message = "Não pode ser vazio")
	private String senha;
}
