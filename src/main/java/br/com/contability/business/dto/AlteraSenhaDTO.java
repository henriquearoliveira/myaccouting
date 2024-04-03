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
public class AlteraSenhaDTO {

    @NotEmpty(message = "Não pode ser null")
    @NotNull(message = "Não pode ser vazio")
    private String email;

    @NotEmpty(message = "Não pode ser null")
    @NotNull(message = "Não pode ser vazio")
    private String codigo;

    @NotEmpty(message = "Não pode ser null")
    @NotNull(message = "Não pode ser vazio")
    private String password;

    @NotEmpty(message = "Não pode ser null")
    @NotNull(message = "Não pode ser vazio")
    private String confirmPassword;
}
