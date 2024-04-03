package br.com.contability.business;

import br.com.contability.comum.BeanIdentificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {@Index(name = "index_upload_image", columnList = "id"),
        @Index(name = "unique_public_id", columnList = "publicid", unique = true)})
public class UploadImage extends BeanIdentificavel {

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "O id publico não pode ser vazio")
    @NotNull(message = "O id publico não pode ser null")
    private String publicID;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "A assinatura não pode ser vazio")
    @NotNull(message = "A assinatura não pode ser null")
    private String signature;

    @Column(nullable = false, unique = true)
    @NotNull(message = "A largura não pode ser null")
    private Long width;

    @Column(nullable = false, unique = true)
    @NotNull(message = "A altura não pode ser null")
    private Long height;

    @Column
    private String format;

    @Column
    private String resourceType;

    @Column
    private LocalDateTime createAt;

    @Column
    private Long bytes;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "A url não pode ser vazio")
    @NotNull(message = "A url não pode ser null")
    private String url;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "A url segura não pode ser vazio")
    @NotNull(message = "A url segura não pode ser null")
    private String secureUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "uploadImage", targetEntity = Usuario.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
