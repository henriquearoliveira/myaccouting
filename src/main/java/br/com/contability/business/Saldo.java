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
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "saldo", indexes = {@Index(name = "index_saldo", columnList = "id")})
public class Saldo extends BeanIdentificavel {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Usuario usuario;

    @Column
    private BigDecimal saldoAtual;

    @Column
    private BigDecimal saldoProvavel;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataHoraLancamento;
}
