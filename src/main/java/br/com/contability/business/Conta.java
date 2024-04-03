package br.com.contability.business;

import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "conta", indexes = {@Index(name = "index_conta", columnList = "id")})
public class Conta extends BeanIdentificavel {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "A descricao não pode ser vazio")
    @NotNull(message = "A descricao não pode ser null")
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "conta", targetEntity = Lancamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;
}
