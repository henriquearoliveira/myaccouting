package br.com.contability.comum;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BeanIdentificavel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd
    // HH:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    // @DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
    // @Temporal(TemporalType.TIMESTAMP) ACREDITO QUE NÃO FUNCIONA DEVIDO ISSO FORMATAR O JSON
    // E NÃO O FORMATO NO FRONT END
    private LocalDateTime dataHoraCadastro;
    private LocalDateTime dataHoraAtualizacao;
}
