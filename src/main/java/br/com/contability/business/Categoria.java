package br.com.contability.business;

import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "categoria", indexes = {@Index(name = "index_categoria", columnList = "id", unique = false),
        @Index(name = "unique_descricao_usuario", columnList = "descricao,usuario_id", unique = true)})
public class Categoria extends BeanIdentificavel /*implements Comparable<TipoDeCategoria>*/ {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "A descricao não pode ser vazio")
    @NotNull(message = "A descricao não pode ser null")
    private String descricao;

    @NotNull(message = "O tipo de categoria não pode ser null")
    @Enumerated(EnumType.STRING)
    private TipoDeCategoria tipoDeCategoria;

    @Column(length = 200)
    private String observacao;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "categoria", targetEntity = Lancamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;
    //
}
