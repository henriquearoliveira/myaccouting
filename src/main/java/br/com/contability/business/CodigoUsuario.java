package br.com.contability.business;

import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {@Index(name = "index_codigo_usuario", columnList = "id"),
        @Index(name = "unique_codigo_usuario", columnList = "codigo,usuario_id", unique = true)})
public class CodigoUsuario extends BeanIdentificavel {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Column(length = 20, nullable = false)
    @NotEmpty(message = "O codigo não pode ser vazio")
    @NotNull(message = "O codigo não pode ser null")
    private String codigo;

    @Column(nullable = false)
    @NotNull(message = "O status não pode ser null")
    private boolean ativo = true;
}
