package br.com.contability.business;

import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lancamento", indexes = {@Index(name = "index_lancamento", columnList = "id")})
public class Lancamento extends BeanIdentificavel {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Categoria categoria;

    @JsonIgnore
    @ManyToOne
    private Conta conta;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "A descricao não pode ser vazio")
    @NotNull(message = "A descricao não pode ser null")
    private String descricao;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataHoraLancamento;

    @Transient
    private String valorConversao;

    @Column
    private BigDecimal valorLancamento;

    @Column
    private boolean pago;

    @Column
    private boolean parcelado;

    @Column
    private Integer parcelas;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataHoraVencimento;
}
